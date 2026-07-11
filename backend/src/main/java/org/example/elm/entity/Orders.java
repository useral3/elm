package org.example.elm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    private Integer orderId; // 订单编号
    private String userId; // 所属用户编号
    private Integer businessId; // 所属商家编号
    private String orderDate; // 订购日期
    private BigDecimal orderTotal; // 订单总价
    private Integer daId; // 所属送货地址编号
    private Integer orderState; // 订单状态（0:未支付;1:已支付）
    
    // 关联信息
    private Business business; // 所属商家信息
    private List<Orderdetailet> orderDetails; // 订单明细信息
}