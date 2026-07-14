package org.example.elm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Authorization")
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 启用登录拦截器
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/cart/**", "/orders/**", "/deliveryAddress/**",
                                 "/favorite/**", "/user/currentUser", "/user/logout")
                .excludePathPatterns("/user/login", "/user/saveUser", "/user/getUserById",
                                     "/user/sendCode", "/business/**", "/food/**",
                                     "/chat/**", "/static/**", "/uploads/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /static/** 映射到 classpath 下的 static 目录
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");

        // /uploads/** 映射到文件系统
        String uploadPath = "D:/JavaEE/elm/uploads/";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath);
    }
}
