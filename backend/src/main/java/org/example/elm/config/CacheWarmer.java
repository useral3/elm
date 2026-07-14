package org.example.elm.config;

import org.example.elm.entity.Business;
import org.example.elm.entity.Food;
import org.example.elm.mapper.OrdersMapper;
import org.example.elm.service.BusinessService;
import org.example.elm.service.FoodService;
import org.example.elm.service.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 缓存预热：应用启动后自动加载热门数据到 Redis
 */
@Component
public class CacheWarmer implements ApplicationRunner {

    @Autowired
    private BusinessService businessService;

    @Autowired
    private FoodService foodService;

    @Autowired
    private RedisCacheService cacheService;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void run(ApplicationArguments args) {
        new Thread(() -> {
            try {
                System.out.println("[CacheWarmer] Starting cache warm-up...");

                // 预加载所有分类的商家列表（orderTypeId 1~10）
                for (int i = 1; i <= 10; i++) {
                    try {
                        String key = RedisCacheService.businessListKey(i);
                        List<Business> list = businessService.listBusinessByOrderTypeId(i);
                        if (list != null && !list.isEmpty()) {
                            cacheService.set(key, list);
                            System.out.println("[CacheWarmer] Warmed business list orderTypeId=" + i + " count=" + list.size());

                            // 预加载每个商家的菜品列表
                            for (Business biz : list) {
                                try {
                                    String foodKey = RedisCacheService.foodListKey(biz.getBusinessId());
                                    List<Food> foods = foodService.listFoodByBusinessId(biz.getBusinessId());
                                    if (foods != null && !foods.isEmpty()) {
                                        cacheService.set(foodKey, foods);
                                    }
                                } catch (Exception ignored) {
                                }
                            }
                        }
                    } catch (Exception ignored) {
                    }
                }

                // 从数据库同步销量到 Redis 排行榜
                try {
                    List<Map<String, Object>> salesList = ordersMapper.countSalesByBusiness();
                    if (salesList != null && !salesList.isEmpty()) {
                        for (Map<String, Object> row : salesList) {
                            Object bizId = row.get("businessId");
                            Object cnt = row.get("cnt");
                            if (bizId != null && cnt != null) {
                                redisTemplate.opsForZSet().add("leaderboard:sales",
                                        bizId.toString(), ((Number) cnt).doubleValue());
                            }
                        }
                        System.out.println("[CacheWarmer] Seeded leaderboard sales: " + salesList.size() + " businesses");
                    }
                } catch (Exception ignored) {
                }

                // 初始化秒杀商品
                try {
                    Map<String, String> flash1 = new HashMap<>();
                    flash1.put("foodId", "1"); flash1.put("foodName", "麻辣烫小份(秒杀)");
                    flash1.put("flashPrice", "0.99"); flash1.put("originalPrice", "18.00");
                    flash1.put("businessId", "1");
                    redisTemplate.opsForHash().putAll("flash:food:1", flash1);
                    redisTemplate.opsForValue().set("flash:stock:1", "100");
                    System.out.println("[CacheWarmer] Seeded flash sale items");
                } catch (Exception ignored) {
                }

                System.out.println("[CacheWarmer] Cache warm-up completed");
            } catch (Exception e) {
                System.err.println("[CacheWarmer] Warm-up failed: " + e.getMessage());
            }
        }, "cache-warmer").start();
    }
}
