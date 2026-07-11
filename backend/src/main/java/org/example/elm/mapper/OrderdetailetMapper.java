package org.example.elm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.elm.entity.Orderdetailet;

import java.util.List;

@Mapper
public interface OrderdetailetMapper {
    List<Orderdetailet> listOrderdetailetByOrderId(@Param("orderId") Integer orderId);
    
    Orderdetailet getOrderdetailetById(@Param("odId") Integer odId);
}