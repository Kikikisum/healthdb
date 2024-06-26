package com.example.healthdb.controller;

import cn.hutool.db.sql.Order;
import com.example.healthdb.common.BaseResponse;
import com.example.healthdb.model.entity.Orders;
import com.example.healthdb.model.entity.Patient;
import com.example.healthdb.model.request.AddOrdersRequest;
import com.example.healthdb.model.request.AddPatientRequest;
import com.example.healthdb.model.request.DeleteOrdersRequest;
import com.example.healthdb.model.request.UpdateOrdersRequest;
import com.example.healthdb.service.OrdersService;
import com.example.healthdb.service.impl.OrdersServiceImpl;
import com.example.healthdb.utils.ResultUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xwb
 */

@RestController
@RequestMapping("/orders")
public class OrdersController {
     @Resource
    private OrdersService ordersService;

    /**
     * 下单
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Void> addOrders(@RequestBody AddOrdersRequest request)
    {
        ordersService.addOrders(request);
        return ResultUtils.success(null);
    }

    /**
     * 删除订单
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Void> deleteOrders(@RequestBody DeleteOrdersRequest request){
        ordersService.deleteOrders(request);
        return ResultUtils.success(null);
    }


    /**
     * 根据订单状态查询订单
     * @param isFinished
     * @return
     */
    @GetMapping("/{isFinished}")
    public BaseResponse<List<Orders>> queryByIsFinished(@PathVariable Integer isFinished)
    {
        return ResultUtils.success(ordersService.queryByIsFinished(isFinished));
    }

    /**
     * 更新订单状态
     * @param request
     * @return
     */
    @PostMapping("/update/isFinished")
    public BaseResponse<Void> updateOrders(@RequestBody UpdateOrdersRequest request){
        ordersService.updateOrders(request);
        return ResultUtils.success(null);
    }



}
