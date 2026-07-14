package org.example.elm.controller;

import org.example.elm.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 秒杀控制器 — Redis 原子扣库存 + 分布式锁
 *
 * 核心设计：
 *   flash:stock:{foodId}      → 剩余库存数（String）
 *   flash:lock:{userId}:{foodId} → 防重复下单锁（SETNX, 5秒过期）
 *   flash:food:{foodId}       → 秒杀商品详情（Hash）
 */
@RestController
@RequestMapping("/flashSale")
public class FlashSaleController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private BusinessService businessService;

    private static final String STOCK_PREFIX = "flash:stock:";
    private static final String LOCK_PREFIX = "flash:lock:";
    private static final String FOOD_PREFIX = "flash:food:";

    /** Lua 脚本：原子检查库存并扣减 */
    private static final String DEDUCT_SCRIPT =
            "local stock = redis.call('GET', KEYS[1]) " +
            "if not stock or tonumber(stock) <= 0 then return -1 end " +
            "redis.call('DECR', KEYS[1]) " +
            "return redis.call('GET', KEYS[1])";

    private final DefaultRedisScript<Long> deductRedisScript;

    public FlashSaleController() {
        deductRedisScript = new DefaultRedisScript<>();
        deductRedisScript.setScriptText(DEDUCT_SCRIPT);
        deductRedisScript.setResultType(Long.class);
    }

    /** 获取秒杀商品列表 */
    @PostMapping("/list")
    public List<Map<String, Object>> list() {
        List<Map<String, Object>> result = new ArrayList<>();
        Set<String> keys = redisTemplate.keys(FOOD_PREFIX + "*");
        if (keys != null) {
            for (String key : keys) {
                Map<Object, Object> hash = redisTemplate.opsForHash().entries(key);
                if (!hash.isEmpty()) {
                    Map<String, Object> item = new HashMap<>();
                    item.put("foodId", Integer.valueOf(hash.get("foodId").toString()));
                    item.put("foodName", hash.get("foodName"));
                    item.put("flashPrice", hash.get("flashPrice"));
                    item.put("originalPrice", hash.get("originalPrice"));
                    String stockKey = STOCK_PREFIX + hash.get("foodId");
                    String stock = redisTemplate.opsForValue().get(stockKey);
                    item.put("stock", stock != null ? Integer.valueOf(stock) : 0);
                    int bizId = Integer.parseInt(hash.get("businessId").toString());
                    item.put("business", businessService.getBusinessById(bizId));
                    result.add(item);
                }
            }
        }
        return result;
    }

    /** 秒杀下单：原子扣库存 + 分布式锁 */
    @PostMapping("/buy")
    public Map<String, Object> buy(@RequestParam String userId,
                                    @RequestParam Integer foodId) {
        Map<String, Object> result = new HashMap<>();

        // 1. 分布式锁：防止同一用户重复秒杀同一商品
        String lockKey = LOCK_PREFIX + userId + ":" + foodId;
        Boolean locked = redisTemplate.opsForValue()
                .setIfAbsent(lockKey, "1", 5, TimeUnit.SECONDS);
        if (!Boolean.TRUE.equals(locked)) {
            result.put("success", false);
            result.put("message", "操作太频繁，请稍后再试");
            return result;
        }

        try {
            // 2. Lua 脚本原子扣库存
            String stockKey = STOCK_PREFIX + foodId;
            Long remain = redisTemplate.execute(deductRedisScript, List.of(stockKey));

            if (remain == null || remain < 0) {
                result.put("success", false);
                result.put("message", "已抢光啦！");
                return result;
            }

            // 3. 秒杀成功
            Map<Object, Object> food = redisTemplate.opsForHash().entries(FOOD_PREFIX + foodId);
            result.put("success", true);
            result.put("message", "秒杀成功！");
            result.put("foodId", foodId);
            result.put("foodName", food.get("foodName"));
            result.put("flashPrice", food.get("flashPrice"));
            result.put("businessId", Integer.valueOf(food.get("businessId").toString()));
            result.put("remain", remain);
            return result;
        } finally {
            redisTemplate.delete(lockKey);
        }
    }

    /** 初始化秒杀商品（管理接口） */
    @PostMapping("/init")
    public String init(@RequestParam Integer foodId,
                       @RequestParam String foodName,
                       @RequestParam String flashPrice,
                       @RequestParam String originalPrice,
                       @RequestParam Integer businessId,
                       @RequestParam Integer stock) {
        Map<String, String> hash = new HashMap<>();
        hash.put("foodId", foodId.toString());
        hash.put("foodName", foodName);
        hash.put("flashPrice", flashPrice);
        hash.put("originalPrice", originalPrice);
        hash.put("businessId", businessId.toString());
        redisTemplate.opsForHash().putAll(FOOD_PREFIX + foodId, hash);
        redisTemplate.opsForValue().set(STOCK_PREFIX + foodId, stock.toString());
        return "ok";
    }
}
