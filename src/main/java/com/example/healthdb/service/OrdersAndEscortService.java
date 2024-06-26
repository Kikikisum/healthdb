package com.example.healthdb.service;

import com.example.healthdb.model.request.AddOrderAndEscortRequest;
import com.example.healthdb.model.request.DeleteOrdersAndEscortRequest;

/**
 * @author xwb
 */
public interface OrdersAndEscortService {

    /**
     * 添加订单与陪诊人
     * @param request
     */
    void addOrdersAndEscort(AddOrderAndEscortRequest request);



    /**
     * 根据id删除订单
     * @param request
     */
    void deleteOrders(DeleteOrdersAndEscortRequest request);

}
