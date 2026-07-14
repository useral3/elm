package org.example.elm.controller;

import org.example.elm.entity.Business;
import org.example.elm.service.BusinessService;
import org.example.elm.service.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/business")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisCacheService redisCacheService;

    /**
     * 根据点餐分类编号查询所属商家信息
     */
    @PostMapping("/listBusinessByOrderTypeId")
    public List<Business> listBusinessByOrderTypeId(@RequestParam Integer orderTypeId) {
        return businessService.listBusinessByOrderTypeId(orderTypeId);
    }

    /**
     * 根据商家编号查询商家信息（含浏览历史记录）
     */
    @PostMapping("/getBusinessById")
    public Business getBusinessById(@RequestParam Integer businessId,
                                     @RequestParam(required = false) String userId) {
        Business business = businessService.getBusinessById(businessId);

        // 记录用户浏览历史（去重：先删旧记录再插到最前）
        if (userId != null && !userId.isEmpty() && business != null) {
            String browseKey = RedisCacheService.browseKey(userId);
            redisTemplate.opsForList().remove(browseKey, 0, businessId.toString());
            redisTemplate.opsForList().leftPush(browseKey, businessId.toString());
            redisTemplate.opsForList().trim(browseKey, 0, 19);
            redisTemplate.expire(browseKey, 7, TimeUnit.DAYS);
        }

        return business;
    }

    /**
     * 获取用户浏览历史
     */
    @PostMapping("/browseHistory")
    public List<Business> browseHistory(@RequestParam String userId) {
        String browseKey = RedisCacheService.browseKey(userId);
        List<String> ids = redisTemplate.opsForList().range(browseKey, 0, -1);
        List<Business> result = new ArrayList<>();
        if (ids != null) {
            for (String id : ids) {
                try {
                    Business business = businessService.getBusinessById(Integer.valueOf(id));
                    if (business != null) {
                        result.add(business);
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return result;
    }

    /**
     * 清除商家缓存（数据变更后调用）
     */
    @PostMapping("/clearCache")
    public String clearCache(@RequestParam(required = false) Integer businessId,
                             @RequestParam(required = false) Integer orderTypeId) {
        if (businessId != null) {
            redisCacheService.delete(RedisCacheService.businessKey(businessId));
        }
        if (orderTypeId != null) {
            redisCacheService.delete(RedisCacheService.businessListKey(orderTypeId));
        }
        if (businessId == null && orderTypeId == null) {
            redisCacheService.deleteByPattern(RedisCacheService.BUSINESS_PREFIX + "*");
        }
        return "ok";
    }

    /** 清除全部浏览历史 */
    @PostMapping("/clearBrowseHistory")
    public String clearBrowseHistory(@RequestParam String userId) {
        redisTemplate.delete(RedisCacheService.browseKey(userId));
        return "ok";
    }

    /** 删除单条浏览历史 */
    @PostMapping("/removeBrowseItem")
    public String removeBrowseItem(@RequestParam String userId, @RequestParam Integer businessId) {
        redisTemplate.opsForList().remove(RedisCacheService.browseKey(userId), 0, businessId.toString());
        return "ok";
    }
}
