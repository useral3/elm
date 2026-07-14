package org.example.elm.controller;

import org.example.elm.entity.User;
import org.example.elm.service.RedisCacheService;
import org.example.elm.service.UserService;
import org.example.elm.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 认证控制器 — JWT + Redis
 *
 * Key 设计：
 *   token:session:{userId}   → Token（2小时有效，用于会话管理）
 *   token:blacklist:{token}  → "1"（登出时加入黑名单）
 */
@RestController
@RequestMapping("/user")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisCacheService redisCacheService;

    /** 发送验证码：60 秒冷却，5 分钟有效 */
    @PostMapping("/sendCode")
    public Map<String, Object> sendCode(@RequestParam String phone) {
        Map<String, Object> result = new HashMap<>();

        // 检查冷却时间
        String cooldownKey = RedisCacheService.codeCooldownKey(phone);
        if (Boolean.TRUE.equals(redisTemplate.hasKey(cooldownKey))) {
            result.put("success", false);
            result.put("message", "验证码已发送，请 60 秒后再试");
            return result;
        }

        // 生成 6 位随机验证码
        String code = String.format("%06d", new Random().nextInt(1000000));
        redisTemplate.opsForValue().set(RedisCacheService.codeKey(phone), code, 5, TimeUnit.MINUTES);
        // 设置 60 秒冷却
        redisTemplate.opsForValue().set(cooldownKey, "1", 60, TimeUnit.SECONDS);

        // TODO: 接入短信服务商，目前控制台打印
        System.out.println("[Verification] phone=" + phone + " code=" + code);

        result.put("success", true);
        result.put("message", "验证码已发送");
        return result;
    }

    /** 登录：验证账号密码 → 签发 JWT → 存入 Redis（含限流） */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestParam String userId, @RequestParam String password) {
        Map<String, Object> result = new HashMap<>();

        // ---- 登录限流 ----
        String lockedKey = RedisCacheService.loginLockedKey(userId);
        if (Boolean.TRUE.equals(redisTemplate.hasKey(lockedKey))) {
            result.put("success", false);
            result.put("message", "账号已被锁定，请 5 分钟后再试");
            return result;
        }

        String attemptKey = RedisCacheService.loginAttemptKey(userId);
        Long attempts = redisTemplate.opsForValue().increment(attemptKey, 1);
        if (attempts != null && attempts == 1) {
            redisTemplate.expire(attemptKey, 60, TimeUnit.SECONDS);
        }
        if (attempts != null && attempts > 5) {
            // 触发锁定
            redisTemplate.opsForValue().set(lockedKey, "1", 5, TimeUnit.MINUTES);
            redisTemplate.delete(attemptKey);
            result.put("success", false);
            result.put("message", "登录过于频繁，账号已被锁定 5 分钟");
            return result;
        }
        // ---- 限流结束 ----

        User user = userService.getUserByIdByPass(userId, password);
        if (user == null) {
            result.put("success", false);
            result.put("message", "用户名或密码错误");
            return result;
        }

        // 登录成功，清除限流计数
        redisTemplate.delete(attemptKey);

        String token = jwtUtil.generateToken(userId);
        // 会话存 Redis，2 小时过期
        redisTemplate.opsForValue().set("token:session:" + userId, token, 2, TimeUnit.HOURS);

        result.put("success", true);
        result.put("token", token);
        result.put("tokenType", "Bearer");
        result.put("user", user);
        return result;
    }

    /** 登出：Token 加入黑名单 */
    @PostMapping("/logout")
    public Map<String, Object> logout(@RequestHeader(value = "Authorization", required = false) String auth) {
        Map<String, Object> result = new HashMap<>();

        if (auth == null || !auth.startsWith("Bearer ")) {
            result.put("success", false);
            result.put("message", "未提供 Token");
            return result;
        }

        String token = auth.substring(7);
        if (!jwtUtil.validate(token)) {
            result.put("success", false);
            result.put("message", "Token 已失效");
            return result;
        }

        // 黑名单，存活时间 = Token 剩余有效时间
        String userId = jwtUtil.getUserId(token);
        long remaining = jwtUtil.getExpiration(token) - System.currentTimeMillis();
        if (remaining > 0) {
            redisTemplate.opsForValue().set("token:blacklist:" + token, "1", remaining, TimeUnit.MILLISECONDS);
        }

        // 清理会话
        redisTemplate.delete("token:session:" + userId);

        result.put("success", true);
        result.put("message", "已登出");
        return result;
    }

    /** 获取当前用户信息 */
    @PostMapping("/currentUser")
    public Map<String, Object> currentUser(@RequestHeader(value = "Authorization", required = false) String auth) {
        Map<String, Object> result = new HashMap<>();

        if (auth == null || !auth.startsWith("Bearer ")) {
            result.put("success", false);
            result.put("message", "未登录");
            return result;
        }

        String token = auth.substring(7);
        if (!jwtUtil.validate(token)) {
            result.put("success", false);
            result.put("message", "Token 已过期");
            return result;
        }

        User user = userService.getUserById(jwtUtil.getUserId(token));
        result.put("success", user != null);
        result.put("user", user);
        return result;
    }
}
