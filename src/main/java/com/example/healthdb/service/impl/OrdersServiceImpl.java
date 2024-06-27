package com.example.healthdb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.healthdb.dao.OrdersDao;
import com.example.healthdb.exception.BusinessException;
import com.example.healthdb.exception.ErrorCode;
import com.example.healthdb.model.entity.Orders;
import com.example.healthdb.model.request.AddOrdersRequest;
import com.example.healthdb.model.request.DeleteOrdersRequest;
import com.example.healthdb.model.request.UpdateOrdersRequest;
import com.example.healthdb.service.OrdersService;
import com.example.healthdb.utils.SnowFlakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author xwb
 */

@Slf4j
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersDao, Orders> implements OrdersService {

    @Resource
    SnowFlakeUtils snowFlakeUtils;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 下单
     * @param addOrdersRequest
     */
    @Override
    public void addOrders(AddOrdersRequest addOrdersRequest) {
        try {
            Orders orders= new Orders();
            Long id = snowFlakeUtils.nextId();
            orders.setId(Math.abs(id.intValue()));
            orders.setUid(addOrdersRequest.getUid());
            orders.setPid(addOrdersRequest.getPid());
            orders.setHid(addOrdersRequest.getHid());
            Date startTime = simpleDateFormat.parse(addOrdersRequest.getStartTime());
            orders.setStartTime(startTime);
            Date endTime = simpleDateFormat.parse(addOrdersRequest.getEndTime());
            orders.setEndTime(endTime);
            orders.setTelephoneNumber(addOrdersRequest.getTelephone());
            orders.setRequirement(addOrdersRequest.getRequirement());
            orders.setIsFinished(addOrdersRequest.getIsFinished());
            orders.setCreateTime(new Date());
            orders.setUpdateTime(new Date());
            orders.setIsDelete(0);
            save(orders);
            log.info("用户下单：{}",orders);
        }catch (ParseException e){
            e.printStackTrace();
        }

    }

    /**
     * 根据id删除订单
     * @param request
     */
    @Override
    public void deleteOrders(DeleteOrdersRequest request) {
        Orders orders = getById(request.getId());
        if (orders!=null)
        {
            orders.setUpdateTime(new Date());
            orders.setIsDelete(1);
            updateById(orders);
        }else {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }


    /**
     *根据订单完成状态查询订单
     * @param isFinished
     * @return
     */
    @Override
    public List<Orders> queryByIsFinished(Integer isFinished) {
        LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Orders::getIsFinished,isFinished)
                .eq(Orders::getIsDelete,0);
        return list(lambdaQueryWrapper);
    }


    /**
     * 订单完成
     * @param request
     */
    @Override
    public void updateOrders(UpdateOrdersRequest request) {
        Orders orders = getById(request.getId());
        if (orders!=null)
        {
            orders.setUpdateTime(new Date());
            orders.setIsFinished(1);
            updateById(orders);
        }else {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }

    @Override
    public void checkOverTime() {
        LambdaQueryWrapper<Orders> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.lt(Orders::getEndTime,new Date());
        List<Orders> list=list(lambdaQueryWrapper);
        for (Orders order:list)
        {
            order.setIsFinished(1);
        }
        for (Orders order:list)
        {
            updateById(order);
        }
    }


}
