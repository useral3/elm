package org.example.elm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.elm.entity.Food;

import java.util.List;

@Mapper
public interface FoodMapper {
    /**
     * 根据商家编号查询所属食品信息
     */
    List<Food> listFoodByBusinessId(Integer businessId);
    
    /**
     * 根据食品编号查询食品信息
     */
    Food getFoodById(@Param("foodId") Integer foodId);
}