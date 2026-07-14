package org.example.elm.service;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redis 缓存服务（Cache-Aside 模式）
 * 先查缓存 → 未命中查数据库 → 回写缓存
 */
@Service
public class RedisCacheService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final long DEFAULT_TTL_MINUTES = 30;

    // ========== 通用操作 ==========

    public <T> T get(String key, Class<T> clazz) {
        String json = redisTemplate.opsForValue().get(key);
        if (json == null) {
            System.out.println("[Redis] MISS  -> " + key + " (cache miss)");
            return null;
        }
        System.out.println("[Redis] HIT ✓ " + key);
        return JSON.parseObject(json, clazz);
    }

    public <T> List<T> getList(String key, Class<T> itemClazz) {
        String json = redisTemplate.opsForValue().get(key);
        if (json == null) {
            System.out.println("[Redis] MISS  -> " + key + " (cache miss)");
            return null;
        }
        System.out.println("[Redis] HIT ✓ " + key);
        return JSON.parseArray(json, itemClazz);
    }

    public void set(String key, Object value, long ttlMinutes) {
        redisTemplate.opsForValue().set(key, JSON.toJSONString(value), ttlMinutes, TimeUnit.MINUTES);
    }

    public void set(String key, Object value) {
        set(key, value, DEFAULT_TTL_MINUTES);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void deleteByPattern(String pattern) {
        var keys = redisTemplate.keys(pattern);
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    // ========== 业务 Key ==========
    public static final String BUSINESS_PREFIX = "cache:business:";
    public static final String BUSINESS_LIST_PREFIX = "cache:business:list:";
    public static final String FOOD_PREFIX = "cache:food:";
    public static final String FOOD_LIST_PREFIX = "cache:food:list:";
    public static final String ORDERS_PREFIX = "cache:orders:";
    public static final String USER_PREFIX = "cache:user:";

    // 验证码
    public static final String CODE_PREFIX = "code:";
    public static final String CODE_COOLDOWN_PREFIX = "code:cooldown:";

    // 登录限流
    public static final String LOGIN_ATTEMPT_PREFIX = "login:attempt:";
    public static final String LOGIN_LOCKED_PREFIX = "login:locked:";

    // 浏览历史
    public static final String BROWSE_PREFIX = "browse:";

    public static String businessKey(Integer id) { return BUSINESS_PREFIX + id; }
    public static String businessListKey(Integer orderTypeId) { return BUSINESS_LIST_PREFIX + orderTypeId; }
    public static String foodKey(Integer id) { return FOOD_PREFIX + id; }
    public static String foodListKey(Integer businessId) { return FOOD_LIST_PREFIX + businessId; }
    public static String ordersKey(String userId) { return ORDERS_PREFIX + userId; }
    public static String userKey(String userId) { return USER_PREFIX + userId; }
    public static String codeKey(String phone) { return CODE_PREFIX + phone; }
    public static String codeCooldownKey(String phone) { return CODE_COOLDOWN_PREFIX + phone; }
    public static String loginAttemptKey(String userId) { return LOGIN_ATTEMPT_PREFIX + userId; }
    public static String loginLockedKey(String userId) { return LOGIN_LOCKED_PREFIX + userId; }
    public static String browseKey(String userId) { return BROWSE_PREFIX + userId; }
}
