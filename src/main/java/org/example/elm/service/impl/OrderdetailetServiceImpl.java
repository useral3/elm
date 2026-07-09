package org.example.elm.service.impl;

import org.example.elm.entity.Orderdetailet;
import org.example.elm.mapper.OrderdetailetMapper;
import org.example.elm.service.OrderdetailetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderdetailetServiceImpl implements OrderdetailetService {

    @Autowired
    private OrderdetailetMapper orderdetailetMapper;

    @Override
    public List<Orderdetailet> listOrderdetailetByOrderId(Integer orderId) {
        return orderdetailetMapper.listOrderdetailetByOrderId(orderId);
    }

    @Override
    public Orderdetailet getOrderdetailetById(Integer odId) {
        return orderdetailetMapper.getOrderdetailetById(odId);
    }
}