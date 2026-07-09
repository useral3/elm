package org.example.elm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 商家实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Business {
    private Integer businessId; // 商家编号
    private String businessName; // 商家名称
    private String businessAddress; // 商家地址
    private String businessExplain; // 商家介绍
    private String businessImg; // 商家图片
    private Integer orderTypeId; // 点餐分类
    private BigDecimal starPrice; // 起送费
    private BigDecimal deliveryPrice; // 配送费
    private String remarks; // 备注
}