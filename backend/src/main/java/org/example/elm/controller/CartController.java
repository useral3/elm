package org.example.elm.controller;

import org.example.elm.entity.Cart;
import org.example.elm.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    /**
     * 根据用户编号查询此用户所有购物车信息
     * 根据用户编号和商家编号，查询此用户购物车中某个商家的所有购物车信息
     */
    @PostMapping("/listCart")
    public List<Cart> listCart(@RequestParam String userId, @RequestParam(required = false) Integer businessId) {
        return cartService.listCart(userId, businessId);
    }
    
    /**
     * 向购物车表中添加一条记录
     */
    @PostMapping("/saveCart")
    public int saveCart(@RequestParam String userId, @RequestParam Integer businessId, @RequestParam Integer foodId) {
        return cartService.saveCart(userId, businessId, foodId);
    }
    
    /**
     * 根据用户编号、商家编号、食品编号更新数量
     */
    @PostMapping("/updateCart")
    public int updateCart(@RequestParam String userId, @RequestParam Integer businessId, 
                          @RequestParam Integer foodId, @RequestParam Integer quantity) {
        return cartService.updateCart(userId, businessId, foodId, quantity);
    }
    
    /**
     * 根据用户编号、商家编号、食品编号删除购物车表中的一条食品记录
     * 根据用户编号、商家编号删除购物车表中的多条条记录
     */
    @PostMapping("/removeCart")
    public int removeCart(@RequestParam String userId, @RequestParam Integer businessId, 
                          @RequestParam(required = false) Integer foodId) {
        return cartService.removeCart(userId, businessId, foodId);
    }
}