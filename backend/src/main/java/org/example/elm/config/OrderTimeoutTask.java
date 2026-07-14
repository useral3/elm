package org.example.elm.config;

import org.example.elm.entity.Orders;
import org.example.elm.mapper.OrdersMapper;
import org.example.elm.service.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 定时任务：每分钟扫描超时未支付订单，自动取消
 */
@Component
public class OrderTimeoutTask {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private RedisCacheService cacheService;

    @Scheduled(fixedRate = 60000) // 每 60 秒执行
    public void cancelTimeoutOrders() {
        List<Integer> timeoutIds = ordersMapper.findTimeoutOrders();
        if (timeoutIds != null && !timeoutIds.isEmpty()) {
            for (Integer orderId : timeoutIds) {
                ordersMapper.cancelOrder(orderId);
                // 清除订单缓存
                Orders order = ordersMapper.getOrdersById(orderId);
                if (order != null) {
                    cacheService.delete(RedisCacheService.ordersKey(order.getUserId()));
                }
                System.out.println("[OrderTimeout] Cancelled order: " + orderId);
            }
        }
    }
}
