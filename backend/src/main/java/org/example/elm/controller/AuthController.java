package org.example.elm.controller;

import org.example.elm.entity.User;
import org.example.elm.service.UserService;
import org.example.elm.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
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

    /** 登录：验证账号密码 → 签发 JWT → 存入 Redis */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestParam String userId, @RequestParam String password) {
        Map<String, Object> result = new HashMap<>();

        User user = userService.getUserByIdByPass(userId, password);
        if (user == null) {
            result.put("success", false);
            result.put("message", "用户名或密码错误");
            return result;
        }

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
