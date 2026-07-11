package org.example.elm.service.impl;

import org.example.elm.entity.Business;
import org.example.elm.entity.Cart;
import org.example.elm.entity.Food;
import org.example.elm.service.BusinessService;
import org.example.elm.service.CartService;
import org.example.elm.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 购物车 — 基于 Redis Hash
 *
 * Key 设计：
 *   cart:{userId}:{businessId}  → Hash { foodId : quantity }
 *   cart:user:{userId}          → Set  { businessId }         记录用户有哪些商家购物车
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private FoodService foodService;

    @Autowired
    private BusinessService businessService;

    private String cartKey(String userId, Integer businessId) {
        return "cart:" + userId + ":" + businessId;
    }

    private String cartUserKey(String userId) {
        return "cart:user:" + userId;
    }

    @Override
    public List<Cart> listCart(String userId, Integer businessId) {
        List<Cart> result = new ArrayList<>();
        if (businessId != null) {
            result.addAll(getByBusiness(userId, businessId));
        } else {
            Set<String> bizIds = redisTemplate.opsForSet().members(cartUserKey(userId));
            if (bizIds != null) {
                for (String bid : bizIds) {
                    result.addAll(getByBusiness(userId, Integer.valueOf(bid)));
                }
            }
        }
        return result;
    }

    private List<Cart> getByBusiness(String userId, Integer businessId) {
        List<Cart> list = new ArrayList<>();
        String key = cartKey(userId, businessId);
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
        if (entries.isEmpty()) return list;

        // 通过 Service 查商家（缓存未命中会自动降级到数据库）
        Business business = businessService.getBusinessById(businessId);

        for (Map.Entry<Object, Object> e : entries.entrySet()) {
            Integer foodId = Integer.valueOf(e.getKey().toString());
            Integer qty = Integer.valueOf(e.getValue().toString());

            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setBusinessId(businessId);
            cart.setFoodId(foodId);
            cart.setQuantity(qty);
            cart.setBusiness(business);
            // 通过 Service 查食品（缓存未命中会自动降级到数据库）
            cart.setFood(foodService.getFoodById(foodId));
            list.add(cart);
        }
        return list;
    }

    @Override
    public int saveCart(String userId, Integer businessId, Integer foodId) {
        String key = cartKey(userId, businessId);
        String field = foodId.toString();

        Object old = redisTemplate.opsForHash().get(key, field);
        if (old != null) {
            // 已有 → 数量 +1
            redisTemplate.opsForHash().increment(key, field, 1);
        } else {
            redisTemplate.opsForHash().put(key, field, "1");
        }
        // 记录用户在这个商家有购物车
        redisTemplate.opsForSet().add(cartUserKey(userId), businessId.toString());
        return 1;
    }

    @Override
    public int updateCart(String userId, Integer businessId, Integer foodId, Integer quantity) {
        if (quantity <= 0) {
            return removeCart(userId, businessId, foodId);
        }
        redisTemplate.opsForHash().put(cartKey(userId, businessId), foodId.toString(), quantity.toString());
        return 1;
    }

    @Override
    public int removeCart(String userId, Integer businessId, Integer foodId) {
        String key = cartKey(userId, businessId);
        if (foodId != null) {
            redisTemplate.opsForHash().delete(key, foodId.toString());
            // Hash 空了就清理 key
            if (Boolean.TRUE.equals(redisTemplate.opsForHash().size(key) == 0)) {
                redisTemplate.delete(key);
                redisTemplate.opsForSet().remove(cartUserKey(userId), businessId.toString());
            }
        } else {
            // 删整个商家购物车
            redisTemplate.delete(key);
            redisTemplate.opsForSet().remove(cartUserKey(userId), businessId.toString());
        }
        return 1;
    }

    /**
     * 下单后清空购物车，返回被清空的数据供 OrdersService 使用
     */
    public List<Cart> clearCart(String userId, Integer businessId) {
        List<Cart> items = getByBusiness(userId, businessId);
        removeCart(userId, businessId, null);
        return items;
    }
}
