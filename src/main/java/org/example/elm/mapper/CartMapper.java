package org.example.elm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.elm.entity.Cart;

import java.util.List;

@Mapper
public interface CartMapper {
    /**
     * 根据用户编号查询此用户所有购物车信息
     * 根据用户编号和商家编号，查询此用户购物车中某个商家的所有购物车信息
     */
    List<Cart> listCart(String userId, Integer businessId);

    /**
     * 向购物车表中添加一条记录
     */
    int saveCart(String userId, Integer businessId, Integer foodId);

    /**
     * 根据用户编号、商家编号、食品编号更新数量
     */
    int updateCart(String userId, Integer businessId, Integer foodId, Integer quantity);

    /**
     * 根据用户编号、商家编号、食品编号删除购物车表中的一条食品记录
     * 根据用户编号、商家编号删除购物车表中的多条条记录
     */
    int removeCart(String userId, Integer businessId, Integer foodId);
}