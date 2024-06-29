package com.example.healthdb.controller;

import com.example.healthdb.common.BaseResponse;
import com.example.healthdb.model.dto.OrdersAndEscortDTO;
import com.example.healthdb.model.entity.Orders;
import com.example.healthdb.model.entity.OrdersAndEscort;
import com.example.healthdb.model.request.AddOrderAndEscortRequest;
import com.example.healthdb.model.request.DeleteOrdersAndEscortRequest;
import com.example.healthdb.service.OrdersAndEscortService;
import com.example.healthdb.service.OrdersService;
import com.example.healthdb.utils.ResultUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 陪诊师接单
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Void> addOrdersAndEscort(@RequestBody AddOrderAndEscortRequest request)
    {
        ordersService.checkOverTime();
        ordersAndEscortService.addOrdersAndEscort(request);
        return ResultUtils.success(null);
    }


    /**
     * 根据id删除陪诊师订单
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Void> deleteOrdersAndEscort(@RequestBody DeleteOrdersAndEscortRequest request){
        ordersService.checkOverTime();
        ordersAndEscortService.deleteOrders(request);
        return ResultUtils.success(null);
    }

    /**
     * 根据陪诊师订单完成状况查询陪诊师订单
     * @param isFinished
     * @return
     */
    @GetMapping("/isFinished")
    public BaseResponse<List<OrdersAndEscortDTO>> queryByIsFinished(@RequestParam("isFinished") Integer isFinished, @RequestParam("uid") Integer uid){
        return ResultUtils.success(ordersAndEscortService.queryByIsFinished(isFinished,uid));
    }



}
