package org.example.elm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class UploadConfig {
    
    @Value("${upload.path:./uploads}")
    private String uploadPath;
    
    public String getUploadPath() {
        // 确保使用绝对路径
        File uploadDir = new File(uploadPath);
        if (!uploadDir.isAbsolute()) {
            // 如果是相对路径，转换为绝对路径
            uploadDir = new File(System.getProperty("user.dir"), uploadPath);
        }
        return uploadDir.getAbsolutePath();
    }
    
    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }
}