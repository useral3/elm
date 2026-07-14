package org.example.elm.config;

import org.example.elm.entity.Business;
import org.example.elm.entity.Food;
import org.example.elm.service.BusinessService;
import org.example.elm.service.FoodService;
import org.example.elm.service.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 缓存预热：应用启动后自动加载热门数据到 Redis
 */
@Component
public class CacheWarmer implements ApplicationRunner {

    @Autowired
    private BusinessService businessService;

    @Autowired
    private FoodService foodService;

    @Autowired
    private RedisCacheService cacheService;

    @Override
    public void run(ApplicationArguments args) {
        new Thread(() -> {
            try {
                System.out.println("[CacheWarmer] 开始缓存预热...");

                // 预加载所有分类的商家列表（orderTypeId 1~10）
                for (int i = 1; i <= 10; i++) {
                    try {
                        String key = RedisCacheService.businessListKey(i);
                        List<Business> list = businessService.listBusinessByOrderTypeId(i);
                        if (list != null && !list.isEmpty()) {
                            cacheService.set(key, list);
                            System.out.println("[CacheWarmer] 预热商家列表 orderTypeId=" + i + " 共 " + list.size() + " 条");

                            // 预加载每个商家的菜品列表
                            for (Business biz : list) {
                                try {
                                    String foodKey = RedisCacheService.foodListKey(biz.getBusinessId());
                                    List<Food> foods = foodService.listFoodByBusinessId(biz.getBusinessId());
                                    if (foods != null && !foods.isEmpty()) {
                                        cacheService.set(foodKey, foods);
                                    }
                                } catch (Exception ignored) {
                                }
                            }
                        }
                    } catch (Exception ignored) {
                    }
                }

                System.out.println("[CacheWarmer] 缓存预热完成");
            } catch (Exception e) {
                System.err.println("[CacheWarmer] 预热失败: " + e.getMessage());
            }
        }, "cache-warmer").start();
    }
}
