package org.example.elm.service.impl;

import org.example.elm.entity.User;
import org.example.elm.mapper.UserMapper;
import org.example.elm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public User getUserByIdByPass(String userId, String password) {
        return userMapper.getUserByIdByPass(userId, password);
    }
    
    @Override
    public User getUserById(String userId) {
        return userMapper.getUserById(userId);
    }
    
    @Override
    public int saveUser(String userId, String password, String userName, Integer userSex) {
        return userMapper.saveUser(userId, password, userName, userSex);
    }
    
    @Override
    public int updateUser(String userId, String userName, Integer userSex) {
        return userMapper.updateUser(userId, userName, userSex);
    }

    @Override
    public int updateUserAvatar(String userId, String avatarPath) {
        return userMapper.updateUserAvatar(userId, avatarPath);
    }

}