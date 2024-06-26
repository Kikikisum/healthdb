package com.example.healthdb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.healthdb.dao.OrdersAndEscortDao;
import com.example.healthdb.exception.BusinessException;
import com.example.healthdb.exception.ErrorCode;

import com.example.healthdb.model.entity.OrdersAndEscort;
import com.example.healthdb.model.request.AddOrderAndEscortRequest;
import com.example.healthdb.model.request.DeleteOrdersAndEscortRequest;

import com.example.healthdb.service.OrdersAndEscortService;
import com.example.healthdb.service.OrdersService;
import com.example.healthdb.utils.SnowFlakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author xwb
 */
@Slf4j
@Service
public class OrdersAndEscortServiceImpl extends ServiceImpl<OrdersAndEscortDao, OrdersAndEscort> implements OrdersAndEscortService {

    @Resource
    SnowFlakeUtils snowFlakeUtils;

    /**
     * 下单
     * @param request
     */
    @Override
    public void addOrdersAndEscort(AddOrderAndEscortRequest request) {
        OrdersAndEscort ordersAndEscort = new OrdersAndEscort();
        Long id = snowFlakeUtils.nextId();
        ordersAndEscort.setId(id.intValue());
        //TODO 根据用户id得到陪诊师id
        ordersAndEscort.setOid(request.getOid());
        ordersAndEscort.setCreateTime(new Date());
        ordersAndEscort.setUpdateTime(new Date());
        ordersAndEscort.setIsDelete(0);

        save(ordersAndEscort);
    }

    /**
     * 根据id删除订单
     * @param request
     */
    @Override
    public void deleteOrders(DeleteOrdersAndEscortRequest request) {
        OrdersAndEscort ordersAndEscort = getById(request.getId());
        log.info("删除订单：{}",ordersAndEscort);
        if (ordersAndEscort!=null)
        {
            ordersAndEscort.setUpdateTime(new Date());
            ordersAndEscort.setIsDelete(1);
            updateById(ordersAndEscort);
        }else {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }



}
