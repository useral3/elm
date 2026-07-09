package org.example.elm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.elm.entity.Orders;

import java.util.List;

@Mapper
public interface OrdersMapper {
    /**
     * 根据用户编号、商家编号、订单总金额、送货地址编号向订单表中添加一条记录，并获取自动生成的订单编号
     */
    int createOrders(@Param("userId") String userId, @Param("businessId") Integer businessId, 
                     @Param("daId") Integer daId, @Param("orderTotal") java.math.BigDecimal orderTotal);
    
    /**
     * 插入订单实体
     */
    int insertOrder(Orders order);
    
    /**
     * 插入订单明细
     */
    int insertOrderDetail(@Param("orderId") Integer orderId, @Param("foodId") Integer foodId, 
                          @Param("quantity") Integer quantity);

    /**
     * 根据订单编号查询订单信息，包括所属商家信息，和此订单的所有订单明细信息
     */
    Orders getOrdersById(@Param("orderId") Integer orderId);

    /**
     * 根据用户编号查询此用户的所有订单信息
     */
    List<Orders> listOrdersByUserId(@Param("userId") String userId);

    /**
     * 根据订单编号更新订单状态为已支付
     */
    int updateOrderPayStatus(@Param("orderId") Integer orderId);
}