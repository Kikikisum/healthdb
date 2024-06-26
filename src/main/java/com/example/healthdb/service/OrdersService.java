package com.example.healthdb.service;

import com.example.healthdb.model.request.AddOrdersRequest;
import com.example.healthdb.model.request.DeleteOrdersRequest;

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
}
