package org.example.elm.service.impl;

import org.example.elm.entity.Food;
import org.example.elm.mapper.FoodMapper;
import org.example.elm.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodMapper foodMapper;

    @Override
    public List<Food> listFoodByBusinessId(Integer businessId) {
        return foodMapper.listFoodByBusinessId(businessId);
    }

    @Override
    public Food getFoodById(Integer foodId) {
        return foodMapper.getFoodById(foodId);
    }
}