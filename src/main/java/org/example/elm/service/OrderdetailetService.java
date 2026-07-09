package org.example.elm.service;

import org.example.elm.entity.Orderdetailet;

import java.util.List;

public interface OrderdetailetService {
    /**
     * 根据订单编号查询订单明细信息
     */
    List<Orderdetailet> listOrderdetailetByOrderId(Integer orderId);
    
    /**
     * 根据订单明细编号查询订单明细信息
     */
    Orderdetailet getOrderdetailetById(Integer odId);
}