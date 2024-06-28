package com.example.healthdb.controller;

import com.example.healthdb.common.BaseResponse;
import com.example.healthdb.model.dto.OrdersAndEscortDTO;
import com.example.healthdb.model.dto.OrdersDTO;
import com.example.healthdb.model.entity.Orders;
import com.example.healthdb.model.request.AddEscortRequest;
import com.example.healthdb.service.EscortService;
import com.example.healthdb.service.OrdersService;
import com.example.healthdb.utils.ResultUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/escort")
public class EscortController {

    @Resource
    private EscortService escortService;

    @Resource
    private OrdersService ordersService;

    /**
     * 申请成为陪诊师
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Void> addEscort(@RequestBody AddEscortRequest request)
    {
        escortService.addEscort(request);
        return ResultUtils.success(null);
    }

    /**
     * 查询陪诊师可接单订单
     * @param uid
     * @return
     */
    @GetMapping("/query/by/{uid}")
    public BaseResponse<List<OrdersAndEscortDTO>> queryAvailableOrders(@PathVariable Integer uid){
        return ResultUtils.success(ordersService.queryAvailableOrders(uid));
    }
}
