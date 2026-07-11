package org.example.elm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 食品实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {
    private Integer foodId; // 食品编号
    private String foodName; // 食品名称
    private String foodExplain; // 食品介绍
    private String foodImg; // 食品图片
    private BigDecimal foodPrice; // 食品价格
    private Integer businessId; // 所属商家编号
    private String remarks; // 备注
}