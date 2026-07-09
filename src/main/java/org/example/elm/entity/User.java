package org.example.elm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String userId; // 用户编号
    @JsonIgnore
    private String password; // 密码
    private String userName; // 用户名称
    private Integer userSex; // 用户性别（1:男;0:女）
    private String userImg; // 用户头像
    private Integer delTag; // 删除标记（1:正常;0:删除）
}