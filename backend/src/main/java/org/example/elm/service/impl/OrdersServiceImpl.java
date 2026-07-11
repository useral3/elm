package org.example.elm.service.impl;

import org.example.elm.entity.Cart;
import org.example.elm.entity.Orders;
import org.example.elm.mapper.OrdersMapper;
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
    private CartServiceImpl cartService;

    @Override
    @Transactional
    public Integer createOrders(String userId, Integer businessId, Integer daId, java.math.BigDecimal orderTotal) {
        // 1. 向订单表插入记录
        Orders order = new Orders();
        order.setUserId(userId);
        order.setBusinessId(businessId);
        order.setDaId(daId);
        order.setOrderTotal(orderTotal);
        order.setOrderDate(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        order.setOrderState(0);

        int result = ordersMapper.insertOrder(order);

        if (result > 0 && order.getOrderId() != null) {
            // 2. 从 Redis 购物车查询数据
            List<Cart> cartList = cartService.listCart(userId, businessId);

            // 3. 写入订单明细
            for (Cart cart : cartList) {
                ordersMapper.insertOrderDetail(order.getOrderId(), cart.getFoodId(), cart.getQuantity());
            }

            // 4. 清空 Redis 购物车
            cartService.clearCart(userId, businessId);
        }

        return order.getOrderId();
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