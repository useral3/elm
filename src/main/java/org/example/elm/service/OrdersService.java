package org.example.elm.service;

import org.example.elm.entity.Orders;
import org.example.elm.entity.Orderdetailet;

import java.util.List;

public interface OrdersService {
    /**
     * 根据用户编号、商家编号、订单总金额、送货地址编号向订单表中添加一条记录，
     * 并获取自动生成的订单编号，然后根据用户编号、商家编号从购物车表中查询所有数据，
     * 批量添加到订单明细表中，然后根据用户编号、商家编号删除购物车表中的数据。
     */
    Integer createOrders(String userId, Integer businessId, Integer daId, java.math.BigDecimal orderTotal);

    /**
     * 根据订单编号查询订单信息，包括所属商家信息，和此订单的所有订单明细信息
     */
    Orders getOrdersById(Integer orderId);

    /**
     * 根据用户编号查询此用户的所有订单信息
     */
    List<Orders> listOrdersByUserId(String userId);

    /**
     * 根据订单编号更新订单支付状态
     */
    int updateOrderPayStatus(Integer orderId);
    

}