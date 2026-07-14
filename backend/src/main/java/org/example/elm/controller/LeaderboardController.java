package org.example.elm.controller;

import org.example.elm.entity.Business;
import org.example.elm.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private BusinessService businessService;

    private static final String SALES_KEY = "leaderboard:sales";

    /** 订单支付成功后增加销量（供 OrdersController 调用） */
    public void incrSales(Integer businessId, int delta) {
        redisTemplate.opsForZSet().incrementScore(SALES_KEY, businessId.toString(), delta);
    }

    /** 获取销量排行榜 Top N */
    @PostMapping("/topSales")
    public List<Map<String, Object>> topSales(@RequestParam(defaultValue = "5") int top) {
        List<Map<String, Object>> result = new ArrayList<>();

        Set<ZSetOperations.TypedTuple<String>> set =
                redisTemplate.opsForZSet().reverseRangeWithScores(SALES_KEY, 0, top - 1);

        if (set != null) {
            for (ZSetOperations.TypedTuple<String> tuple : set) {
                Map<String, Object> item = new HashMap<>();
                Integer bizId = Integer.valueOf(tuple.getValue());
                item.put("businessId", bizId);
                item.put("score", tuple.getScore() != null ? tuple.getScore().longValue() : 0);
                // 关联商家详情
                item.put("business", businessService.getBusinessById(bizId));
                result.add(item);
            }
        }
        return result;
    }
}
