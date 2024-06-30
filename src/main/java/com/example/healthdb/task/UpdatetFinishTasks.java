package com.example.healthdb.task;

import com.example.healthdb.service.OrdersService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UpdatetFinishTasks {

    private final OrdersService ordersService;

    /**
     * 注入订单服务类
     * @param ordersService
     */
    public UpdatetFinishTasks(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    /**
     * 每天凌晨1点执行一次，更新订单状态
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void update() {
        // 调用订单服务方法来更新订单状态
        ordersService.autoCheckTime();
    }

}
