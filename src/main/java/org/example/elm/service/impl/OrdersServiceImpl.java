package org.example.elm.service.impl;

import org.example.elm.entity.Cart;
import org.example.elm.entity.Orders;
import org.example.elm.entity.Orderdetailet;
import org.example.elm.mapper.CartMapper;
import org.example.elm.mapper.OrdersMapper;
import org.example.elm.service.OrderdetailetService;
import org.example.elm.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private OrderdetailetService orderdetailetService;

    @Override
    @Transactional
    public Integer createOrders(String userId, Integer businessId, Integer daId, java.math.BigDecimal orderTotal) {
        // 1. 根据用户编号、商家编号、订单总金额、送货地址编号向订单表中添加一条记录
        Orders order = new Orders();
        order.setUserId(userId);
        order.setBusinessId(businessId);
        order.setDaId(daId);
        order.setOrderTotal(orderTotal);
        order.setOrderDate(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))); // 设置当前日期时间
        order.setOrderState(0); // 默认订单状态为未支付

        int result = ordersMapper.insertOrder(order);

        if (result > 0 && order.getOrderId() != null) {
            // 2. 根据用户编号、商家编号从购物车表中查询所有数据
            List<Cart> cartList = cartMapper.listCart(userId, businessId);

            // 3. 批量添加到订单明细表中（这里简化为逐个添加）
            for (Cart cart : cartList) {
                // 插入订单明细
                ordersMapper.insertOrderDetail(order.getOrderId(), cart.getFoodId(), cart.getQuantity());
            }

            // 4. 根据用户编号、商家编号删除购物车表中的数据
            cartMapper.removeCart(userId, businessId, null);
        }

        return order.getOrderId(); // 返回生成的订单ID
    }

    @Override
    public Orders getOrdersById(Integer orderId) {
        return ordersMapper.getOrdersById(orderId);
    }

    @Override
    public List<Orders> listOrdersByUserId(String userId) {
        return ordersMapper.listOrdersByUserId(userId);
    }

    @Override
    public int updateOrderPayStatus(Integer orderId) {
        return ordersMapper.updateOrderPayStatus(orderId);
    }


}