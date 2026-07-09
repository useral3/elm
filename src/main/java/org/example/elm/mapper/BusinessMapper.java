package org.example.elm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.elm.entity.Business;
import java.util.List;

@Mapper
public interface BusinessMapper {
    
    /**
     * 根据点餐分类编号查询所属商家信息
     */
    List<Business> listBusinessByOrderTypeId(@Param("orderTypeId") Integer orderTypeId);
    
    /**
     * 根据商家编号查询商家信息
     */
    Business getBusinessById(@Param("businessId") Integer businessId);
}