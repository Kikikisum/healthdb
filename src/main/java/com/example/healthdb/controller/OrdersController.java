package com.example.healthdb.controller;

import com.example.healthdb.common.BaseResponse;

import com.example.healthdb.model.dto.OrdersAndEscortDTO;
import com.example.healthdb.model.dto.OrdersDTO;
import com.example.healthdb.model.request.AddOrdersRequest;
import com.example.healthdb.model.request.DeleteOrdersRequest;
import com.example.healthdb.model.request.MutipleQueryOrdersRequest;
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
     * 更新订单状态
     * @param request
     * @return
     */
    @PostMapping("/update/status")
    public BaseResponse<Void> updateOrdersStatus(@RequestBody UpdateOrdersRequest request){
        ordersService.autoCheckTime();
        ordersService.updateOrders(request);
        return  ResultUtils.success(null);
    }


    /**
     * 根据订单完成状况查询订单
     * @param status
     * @return
     */
    @GetMapping("/status")
   public BaseResponse<List<OrdersDTO>> queryByStatus(@RequestParam("status") Integer status,@RequestParam("uid") Integer uid){
        ordersService.autoCheckTime();
        return ResultUtils.success(ordersService.queryByStatus(status,uid));
   }

    /**
     * 根据订单id查询订单
      * @param id
     * @return
     */
   @GetMapping("/query/by/{id}")
   public BaseResponse<OrdersDTO> queryById(@PathVariable Integer id){
       ordersService.autoCheckTime();
       return ResultUtils.success(ordersService.queryById(id));
   }

    /**
     * 根据多条件对订单进行查询
     * @param request
     * @return
     */
    @PostMapping("/query/multiple")
    public BaseResponse<List<OrdersAndEscortDTO>> queryByMultipleQuery(@RequestBody MutipleQueryOrdersRequest request)
    {
        return ResultUtils.success(ordersService.queryByMutipleConditions(request));
    }

    /**
     * 手动更新订单状态
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Void> update()
    {
        ordersService.autoCheckTime();
        return ResultUtils.success(null);
    }


}
