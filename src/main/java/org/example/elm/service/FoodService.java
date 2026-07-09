package org.example.elm.service;

import org.example.elm.entity.Food;

import java.util.List;

public interface FoodService {
    /**
     * 根据商家编号查询所属食品信息
     */
    List<Food> listFoodByBusinessId(Integer businessId);
    
    /**
     * 根据食品编号查询食品信息
     */
    Food getFoodById(Integer foodId);
}