package com.example.healthdb.controller;

import cn.hutool.db.sql.Order;
import com.example.healthdb.common.BaseResponse;
import com.example.healthdb.model.request.AddOrdersRequest;
import com.example.healthdb.model.request.AddPatientRequest;
import com.example.healthdb.model.request.DeleteOrdersRequest;
import com.example.healthdb.service.OrdersService;
import com.example.healthdb.service.impl.OrdersServiceImpl;
import com.example.healthdb.utils.ResultUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xwb
 */

@RestController
@RequestMapping("/orders")
public class OrdersController {
     @Resource
    private OrdersService ordersService;
    @PostMapping("/add")
    public BaseResponse<Void> addOrders(@RequestBody AddOrdersRequest request)
    {
        ordersService.addOrders(request);
        return ResultUtils.success(null);
    }

    @PostMapping("/delete")
    public BaseResponse<Void> deleteOrders(@RequestBody DeleteOrdersRequest request){
        ordersService.deleteOrders(request);
        return ResultUtils.success(null);
    }



}
