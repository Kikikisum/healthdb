package com.example.healthdb.service;

import com.example.healthdb.model.entity.Orders;
import com.example.healthdb.model.request.AddOrdersRequest;
import com.example.healthdb.model.request.DeleteOrdersRequest;
import com.example.healthdb.model.request.UpdateOrdersRequest;

import java.util.List;

/**
 * @author xwb
 */
public interface OrdersService {

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
    List<Orders> queryByIsFinished(Integer isFinished);

    /**
     * 更新订单完成状态
     * @param request
     */
    void updateOrders(UpdateOrdersRequest request);

    /**
     * 检查订单是否已经完成
     */
    void checkOverTime();
}
