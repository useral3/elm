package org.example.elm.controller;

import org.example.elm.entity.Business;
import org.example.elm.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business")
public class BusinessController {
    
    @Autowired
    private BusinessService businessService;
    
    /**
     * 根据点餐分类编号查询所属商家信息
     */
    @PostMapping("/listBusinessByOrderTypeId")
    public List<Business> listBusinessByOrderTypeId(@RequestParam Integer orderTypeId) {
        return businessService.listBusinessByOrderTypeId(orderTypeId);
    }
    
    /**
     * 根据商家编号查询商家信息
     */
    @PostMapping("/getBusinessById")
    public Business getBusinessById(@RequestParam Integer businessId) {
        return businessService.getBusinessById(businessId);
    }
}