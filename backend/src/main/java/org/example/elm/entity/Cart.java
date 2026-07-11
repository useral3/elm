package org.example.elm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 购物车实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    private Integer cartId; // 无意义编号
    private Integer foodId; // 食品编号
    private Integer businessId; // 所属商家编号
    private String userId; // 所属用户编号
    private Integer quantity; // 同一类型食品的购买数量
    private Food food;
    private Business business;
}