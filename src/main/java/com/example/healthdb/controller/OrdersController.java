package com.example.healthdb.controller;

import com.example.healthdb.common.BaseResponse;

import com.example.healthdb.model.dto.OrdersAndEscortDTO;
import com.example.healthdb.model.dto.OrdersDTO;
import com.example.healthdb.model.request.AddOrdersRequest;
import com.example.healthdb.model.request.DeleteOrdersRequest;
import com.example.healthdb.model.request.UpdateOrdersRequest;
import com.example.healthdb.service.OrdersService;
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
     * 用户下单
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
     * 根据id删除订单
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Void> deleteOrders(@RequestBody DeleteOrdersRequest request){
        ordersService.deleteOrders(request);
        return ResultUtils.success(null);
    }

    /**
     * 更新订单完成情况
     * @param request
     * @return
     */
    @PostMapping("/update/isFinished")
    public BaseResponse<Void> updateOrdersIsFinished(@RequestBody UpdateOrdersRequest request){
        ordersService.checkOverTime();
        ordersService.updateOrders(request);
        return  ResultUtils.success(null);
    }


    /**
     * 根据订单完成状况查询订单
     * @param isFinished
     * @return
     */
    @GetMapping("/isFinished")
   public BaseResponse<List<OrdersAndEscortDTO>> queryByIsFinished(@RequestParam("isFinished") Integer isFinished,@RequestParam("uid") Integer uid){
        ordersService.checkOverTime();
        return ResultUtils.success(ordersService.queryByIsFinished(isFinished,uid));
   }

    /**
     * 根据订单id查询订单
      * @param id
     * @return
     */
   @GetMapping("/query/by/{id}")
   public BaseResponse<OrdersAndEscortDTO> queryById(@PathVariable Integer id){
       ordersService.checkOverTime();
       return ResultUtils.success(ordersService.queryById(id));
   }



}
