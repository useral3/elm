package org.example.elm.controller;

import org.example.elm.config.UploadConfig;
import org.example.elm.entity.User;
import org.example.elm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @PostMapping("/getUserByIdByPass")
    public User getUserByIdByPass(@RequestParam String userId, @RequestParam String password) {
        return userService.getUserByIdByPass(userId, password);
    }
    
    @PostMapping("/getUserById")
    public User getUserById(@RequestParam String userId) {
        return userService.getUserById(userId);
    }
    
    @PostMapping("/saveUser")
    public int saveUser(@RequestParam String userId, @RequestParam String password, 
                       @RequestParam String userName, @RequestParam Integer userSex) {
        return userService.saveUser(userId, password, userName, userSex);
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
            
            // 获取项目根目录
            String projectPath = System.getProperty("user.dir");
            // 创建上传目录
            String uploadDir = projectPath + "/uploads";
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