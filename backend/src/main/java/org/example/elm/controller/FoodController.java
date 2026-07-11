package org.example.elm.controller;

import org.example.elm.entity.Food;
import org.example.elm.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodController {
    
    @Autowired
    private FoodService foodService;
    
    /**
     * 根据商家编号查询所属食品信息
     */
    @PostMapping("/listFoodByBusinessId")
    public List<Food> listFoodByBusinessId(@RequestParam Integer businessId) {
        return foodService.listFoodByBusinessId(businessId);
    }
    
    /**
     * 根据食品编号查询食品信息
     */
    @PostMapping("/getFoodById")
    public Food getFoodById(@RequestParam Integer foodId) {
        return foodService.getFoodById(foodId);
    }
}