package com.example.healthdb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.healthdb.common.BaseResponse;
import com.example.healthdb.dao.OrdersAndEscortDao;
import com.example.healthdb.exception.BusinessException;
import com.example.healthdb.exception.ErrorCode;

import com.example.healthdb.model.entity.Escort;
import com.example.healthdb.model.entity.Orders;
import com.example.healthdb.model.entity.OrdersAndEscort;
import com.example.healthdb.model.request.AddOrderAndEscortRequest;
import com.example.healthdb.model.request.DeleteOrdersAndEscortRequest;

import com.example.healthdb.service.EscortService;
import com.example.healthdb.service.OrdersAndEscortService;
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

    @Resource
    EscortService escortService;

    /**
     * 下单
     * @param request
     */
    @Override
    public void addOrdersAndEscort(AddOrderAndEscortRequest request) {
        OrdersAndEscort ordersAndEscort = new OrdersAndEscort();
        Long id = snowFlakeUtils.nextId();
        ordersAndEscort.setId(Math.abs(id.intValue()));
        // 根据用户id得到陪诊师id
        Escort escort = escortService.getById(request.getUid());
        ordersAndEscort.setEid(escort.getId());
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


    /**
     * 根据订单id查询陪诊人订单
     * @param oid
     * @return
     */
    @Override
    public OrdersAndEscort queryByOid(Integer oid) {
        LambdaQueryWrapper<OrdersAndEscort> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrdersAndEscort::getOid,oid);
        OrdersAndEscort ordersAndEscort = getOne(lambdaQueryWrapper);
        return ordersAndEscort;
    }

}
