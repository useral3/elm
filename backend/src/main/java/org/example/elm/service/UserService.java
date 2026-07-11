package org.example.elm.service;

import org.example.elm.entity.User;

public interface UserService {
    /**
     * 根据用户ID和密码查询用户信息（用于登录）
     */
    User getUserByIdByPass(String userId, String password);
    
    /**
     * 根据用户ID查询用户表返回的行数
     */
    User getUserById(String userId);
    
    /**
     * 插入用户信息
     */
    int saveUser(String userId, String password, String userName, Integer userSex);
    
    /**
     * 更新用户信息
     */
    int updateUser(String userId, String userName, Integer userSex);
    
    /**
     * 上传用户头像
     */
    int updateUserAvatar(String userId, String avatarPath);
}