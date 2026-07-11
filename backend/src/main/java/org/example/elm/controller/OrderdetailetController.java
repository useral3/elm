package org.example.elm.controller;

import org.example.elm.entity.Orderdetailet;
import org.example.elm.service.OrderdetailetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderdetailet")
public class OrderdetailetController {

    @Autowired
    private OrderdetailetService orderdetailetService;

    /**
     * 根据订单编号查询订单明细信息
     */
    @PostMapping("/listOrderdetailetByOrderId")
    public List<Orderdetailet> listOrderdetailetByOrderId(@RequestParam Integer orderId) {
        return orderdetailetService.listOrderdetailetByOrderId(orderId);
    }

    /**
     * 根据订单明细编号查询订单明细信息
     */
    @PostMapping("/getOrderdetailetById")
    public Orderdetailet getOrderdetailetById(@RequestParam Integer odId) {
        return orderdetailetService.getOrderdetailetById(odId);
    }
}