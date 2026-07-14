package org.example.elm.controller;

import org.example.elm.config.UploadConfig;
import org.example.elm.entity.User;
import org.example.elm.service.RedisCacheService;
import org.example.elm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UploadConfig uploadConfig;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping("/getUserByIdByPass")
    public User getUserByIdByPass(@RequestParam String userId, @RequestParam String password) {
        return userService.getUserByIdByPass(userId, password);
    }

    @PostMapping("/getUserById")
    public User getUserById(@RequestParam String userId) {
        return userService.getUserById(userId);
    }

    @PostMapping("/saveUser")
    public Map<String, Object> saveUser(@RequestParam String userId, @RequestParam String password,
                       @RequestParam String userName, @RequestParam Integer userSex,
                       @RequestParam(required = false) String code) {
        Map<String, Object> result = new HashMap<>();

        // 验证码校验：如果提供了验证码参数则校验
        if (code != null && !code.isEmpty()) {
            String codeKey = RedisCacheService.codeKey(userId);
            String savedCode = redisTemplate.opsForValue().get(codeKey);
            if (savedCode == null) {
                result.put("success", false);
                result.put("message", "验证码已过期，请重新获取");
                return result;
            }
            if (!savedCode.equals(code)) {
                result.put("success", false);
                result.put("message", "验证码错误");
                return result;
            }
            // 验证通过，删除验证码
            redisTemplate.delete(codeKey);
        }

        int rows = userService.saveUser(userId, password, userName, userSex);
        result.put("success", rows > 0);
        result.put("message", rows > 0 ? "注册成功" : "注册失败");
        return result;
    }
    
    @PostMapping("/updateUser")
    public int updateUser(@RequestParam String userId, @RequestParam String userName, 
                         @RequestParam Integer userSex) {
        return userService.updateUser(userId, userName, userSex);
    }
    
    @PostMapping("/uploadAvatar")
    public Map<String, Object> uploadAvatar(@RequestParam("file") MultipartFile file, 
                                          @RequestParam("userId") String userId) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 检查文件是否为空
            if (file.isEmpty()) {
                result.put("success", false);
                result.put("message", "文件不能为空");
                return result;
            }
            
            // 检查文件类型
            String contentType = file.getContentType();
            if (contentType != null && !contentType.startsWith("image/")) {
                result.put("success", false);
                result.put("message", "只允许上传图片文件");
                return result;
            }
            
            // 检查文件大小（限制为5MB）
            if (file.getSize() > 5 * 1024 * 1024) {
                result.put("success", false);
                result.put("message", "文件大小不能超过5MB");
                return result;
            }
            
            // 使用 UploadConfig 的统一上传路径
            String uploadDir = uploadConfig.getUploadPath();
            File uploadDirectory = new File(uploadDir);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }
            
            // 生成唯一的文件名
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String newFileName = userId + "_" + System.currentTimeMillis() + fileExtension;
            
            // 保存文件
            String filePath = uploadDir + "/" + newFileName;
            File destFile = new File(filePath);
            file.transferTo(destFile);
            
            // 更新数据库中的用户头像路径
            String avatarPath = "/uploads/" + newFileName;
            int updateResult = userService.updateUserAvatar(userId, avatarPath);
            
            if (updateResult > 0) {
                result.put("success", true);
                result.put("imageUrl", avatarPath);
                result.put("message", "头像上传成功");
            } else {
                result.put("success", false);
                result.put("message", "头像上传失败");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "头像上传异常: " + e.getMessage());
        }
        
        return result;
    }
}