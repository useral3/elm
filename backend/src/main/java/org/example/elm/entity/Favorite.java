package org.example.elm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 收藏实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {
    private Integer favoriteId;
    private String userId;       // 用户编号
    private Integer businessId;  // 商家编号
    private String createTime;   // 收藏时间

    // 关联查询用
    private Business business;   // 商家详情
    private Integer favoriteCount; // 该商家被收藏次数
}
