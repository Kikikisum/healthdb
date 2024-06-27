package com.example.healthdb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.healthdb.model.dto.OrdersAndEscortDTO;
import com.example.healthdb.model.dto.OrdersDTO;
import com.example.healthdb.model.entity.Orders;
import com.example.healthdb.model.request.AddOrdersRequest;
import com.example.healthdb.model.request.DeleteOrdersRequest;
import com.example.healthdb.model.request.MutipleQueryOrdersRequest;
import com.example.healthdb.model.request.UpdateOrdersRequest;

import java.util.List;

/**
 * @author xwb
 */
public interface OrdersService extends IService<Orders> {

    /**
     * 添加订单
     * @param request
     */
    void addOrders(AddOrdersRequest request);


    /**
     * 删除订单
     * @param request
     */
    void deleteOrders(DeleteOrdersRequest request);


    /**
     * 根据订单状态查询
     * @param isFinished
     * @return
     */
    List<OrdersDTO> queryByIsFinished(Integer isFinished);

    /**
     * 更新订单完成状态
     * @param request
     */
    void updateOrders(UpdateOrdersRequest request);

    /**
     * 检查订单是否已经完成
     */
    void checkOverTime();

    /**
     * 根据id查询订单
     * @param id
     * @return
     */
    OrdersAndEscortDTO queryById(Integer id);

    /**
     * 多条件查询
     * @param request
     * @return
     */
    OrdersAndEscortDTO queryByMutipleConditions(MutipleQueryOrdersRequest request);
}
