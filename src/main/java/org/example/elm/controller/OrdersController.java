package org.example.elm.controller;

import org.example.elm.entity.Orders;
import org.example.elm.entity.Orderdetailet;
import org.example.elm.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    
    @Autowired
    private OrdersService ordersService;
    
    /**
     * 根据用户编号、商家编号、订单总金额、送货地址编号向订单表中添加一条记录，
     * 并获取自动生成的订单编号，然后根据用户编号、商家编号从购物车表中查询所有数据，
     * 批量添加到订单明细表中，然后根据用户编号、商家编号删除购物车表中的数据。
     */
    @PostMapping("/createOrders")
    public Integer createOrders(@RequestParam String userId, @RequestParam Integer businessId, 
                           @RequestParam Integer daId, @RequestParam java.math.BigDecimal orderTotal) {
        return ordersService.createOrders(userId, businessId, daId, orderTotal);
    }
    
    /**
     * 根据订单编号查询订单信息，包括所属商家信息，和此订单的所有订单明细信息
     */
    @PostMapping("/getOrdersById")
    public Orders getOrdersById(@RequestParam Integer orderId) {
        return ordersService.getOrdersById(orderId);
    }
    
    /**
     * 根据用户编号查询此用户的所有订单信息
     */
    @PostMapping("/listOrdersByUserId")
    public List<Orders> listOrdersByUserId(@RequestParam String userId) {
        return ordersService.listOrdersByUserId(userId);
    }

    /**
     * 根据订单编号修改订单支付状态为已支付
     */
    @PostMapping("/updateOrderPayStatus")
    public int updateOrderPayStatus(@RequestParam Integer orderId) {
        return ordersService.updateOrderPayStatus(orderId);
    }
    

}