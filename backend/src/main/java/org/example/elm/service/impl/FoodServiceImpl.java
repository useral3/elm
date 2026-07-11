package org.example.elm.service.impl;

import org.example.elm.entity.Food;
import org.example.elm.mapper.FoodMapper;
import org.example.elm.service.FoodService;
import org.example.elm.service.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodMapper foodMapper;

    @Autowired
    private RedisCacheService cacheService;

    @Override
    public List<Food> listFoodByBusinessId(Integer businessId) {
        String key = RedisCacheService.foodListKey(businessId);
        // 1. 查缓存
        List<Food> cached = cacheService.getList(key, Food.class);
        if (cached != null) return cached;
        // 2. 查数据库
        List<Food> list = foodMapper.listFoodByBusinessId(businessId);
        // 3. 回写缓存
        if (list != null && !list.isEmpty()) {
            cacheService.set(key, list);
        }
        return list;
    }

    @Override
    public Food getFoodById(Integer foodId) {
        String key = RedisCacheService.foodKey(foodId);
        // 1. 查缓存
        Food cached = cacheService.get(key, Food.class);
        if (cached != null) return cached;
        // 2. 查数据库
        Food food = foodMapper.getFoodById(foodId);
        // 3. 回写缓存
        if (food != null) {
            cacheService.set(key, food);
        }
        return food;
    }
}
