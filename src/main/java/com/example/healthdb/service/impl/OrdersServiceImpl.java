package com.example.healthdb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.healthdb.dao.OrdersDao;
import com.example.healthdb.exception.BusinessException;
import com.example.healthdb.exception.ErrorCode;
import com.example.healthdb.model.entity.Orders;
import com.example.healthdb.model.request.AddOrdersRequest;
import com.example.healthdb.model.request.DeleteOrdersRequest;
import com.example.healthdb.service.OrdersService;
import com.example.healthdb.utils.SnowFlakeUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xwb
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersDao, Orders> implements OrdersService {

    @Resource
    SnowFlakeUtils snowFlakeUtils;


    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void addOrders(AddOrdersRequest addOrdersRequest) {
        try {
            Orders orders= new Orders();
            Long id = snowFlakeUtils.nextId();
            orders.setId(id.intValue());
            orders.setUid(addOrdersRequest.getUid());
            orders.setPid(addOrdersRequest.getPid());
            orders.setHid(addOrdersRequest.getHid());
            Date clinicTime = simpleDateFormat.parse(addOrdersRequest.getClinicTime());
            orders.setClinicTime(clinicTime);
            orders.setTelephoneNumber(addOrdersRequest.getTelephone());
            orders.setRequirement(addOrdersRequest.getRequirement());
            orders.setIsFinished(addOrdersRequest.getIsFinished());
            orders.setCreateTime(new Date());
            orders.setUpdateTime(new Date());
            orders.setIsDelete(0);
            save(orders);
        }catch (ParseException e){
            e.printStackTrace();
        }

    }

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
}
