package com.example.healthdb.controller;

import com.example.healthdb.common.BaseResponse;
import com.example.healthdb.model.request.AddOrderAndEscortRequest;
import com.example.healthdb.model.request.DeleteOrdersAndEscortRequest;
import com.example.healthdb.service.OrdersAndEscortService;
import com.example.healthdb.service.OrdersService;
import com.example.healthdb.utils.ResultUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xwb
 */

@RestController
@RequestMapping("/orders_escort")
public class OrdersAndEscortController {
    @Resource
    private OrdersAndEscortService ordersAndEscortService;

    @Resource
    private OrdersService ordersService;

    @PostMapping("/add")
    public BaseResponse<Void> addOrdersAndEscort(@RequestBody AddOrderAndEscortRequest request)
    {
        ordersService.checkOverTime();
        ordersAndEscortService.addOrdersAndEscort(request);
        return ResultUtils.success(null);
    }


    @PostMapping("/delete")
    public BaseResponse<Void> deleteOrdersAndEscort(@RequestBody DeleteOrdersAndEscortRequest request){
        ordersService.checkOverTime();
        ordersAndEscortService.deleteOrders(request);
        return ResultUtils.success(null);
    }

}
