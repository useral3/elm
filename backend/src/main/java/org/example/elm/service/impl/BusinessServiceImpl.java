package org.example.elm.service.impl;

import org.example.elm.entity.Business;
import org.example.elm.mapper.BusinessMapper;
import org.example.elm.service.BusinessService;
import org.example.elm.service.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private BusinessMapper businessMapper;

    @Autowired
    private RedisCacheService cacheService;

    @Override
    public List<Business> listBusinessByOrderTypeId(Integer orderTypeId) {
        String key = RedisCacheService.businessListKey(orderTypeId);
        // 1. 查缓存
        List<Business> cached = cacheService.getList(key, Business.class);
        if (cached != null) return cached;
        // 2. 查数据库
        List<Business> list = businessMapper.listBusinessByOrderTypeId(orderTypeId);
        // 3. 回写缓存
        if (list != null && !list.isEmpty()) {
            cacheService.set(key, list);
        }
        return list;
    }

    @Override
    public Business getBusinessById(Integer businessId) {
        String key = RedisCacheService.businessKey(businessId);
        // 1. 查缓存
        Business cached = cacheService.get(key, Business.class);
        if (cached != null) return cached;
        // 2. 查数据库
        Business business = businessMapper.getBusinessById(businessId);
        // 3. 回写缓存
        if (business != null) {
            cacheService.set(key, business);
        }
        return business;
    }
}
