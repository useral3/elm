package org.example.elm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.elm.entity.User;


@Mapper
public interface UserMapper {
    /**
     * 根据用户ID和密码查询用户信息（用于登录）
     */
    User getUserByIdByPass(String userId, String password);
    
    /**
     * 根据用户ID查询用户信息
     */
    User getUserById(@Param("userId") String userId);

    /**
     * 插入用户信息（按参数）
     */
    int saveUser(@Param("userId") String userId, @Param("password") String password, 
                 @Param("userName") String userName, @Param("userSex") Integer userSex);
    
    /**
     * 更新用户信息（按参数）
     */
    int updateUser(@Param("userId") String userId, @Param("userName") String userName, 
                   @Param("userSex") Integer userSex);
    
    /**
     * 更新用户头像
     */
    int updateUserAvatar(@Param("userId") String userId, @Param("userImg") String userImg);
}