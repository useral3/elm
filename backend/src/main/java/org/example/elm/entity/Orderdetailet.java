package org.example.elm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单明细实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orderdetailet {
    private Integer odId; // 订单明细编号
    private Integer orderId; // 所属订单编号
    private Integer foodId; // 所属食品编号
    private Integer quantity; // 数量
    private Food food;
    
    // 显式添加getter和setter方法
    public Integer getOdId() {
        return odId;
    }
    
    public void setOdId(Integer odId) {
        this.odId = odId;
    }
    
    public Integer getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    
    public Integer getFoodId() {
        return foodId;
    }
    
    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}