package org.example.elm.service.impl;

import org.example.elm.entity.Cart;
import org.example.elm.mapper.CartMapper;
import org.example.elm.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Override
    public List<Cart> listCart(String userId, Integer businessId) {
        return cartMapper.listCart(userId, businessId);
    }

    @Override
    public int saveCart(String userId, Integer businessId, Integer foodId) {
        return cartMapper.saveCart(userId, businessId, foodId);
    }

    @Override
    public int updateCart(String userId, Integer businessId, Integer foodId, Integer quantity) {
        return cartMapper.updateCart(userId, businessId, foodId, quantity);
    }

    @Override
    public int removeCart(String userId, Integer businessId, Integer foodId) {
        return cartMapper.removeCart(userId, businessId, foodId);
    }
}