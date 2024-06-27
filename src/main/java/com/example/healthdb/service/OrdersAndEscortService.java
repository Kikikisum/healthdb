package com.example.healthdb.service;

import com.example.healthdb.common.BaseResponse;
import com.example.healthdb.model.entity.OrdersAndEscort;
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


    /**
     * 根据订单id查询陪诊人
     * @param oid
     * @return
     */
    OrdersAndEscort queryByOid(Integer oid);
}
