package org.example.elm.service;

import org.example.elm.entity.Business;
import java.util.List;

public interface BusinessService {
    /**
     * 根据点餐分类编号查询所属商家信息
     */
    List<Business> listBusinessByOrderTypeId(Integer orderTypeId);
    
    /**
     * 根据商家编号查询商家信息
     */
    Business getBusinessById(Integer businessId);
}