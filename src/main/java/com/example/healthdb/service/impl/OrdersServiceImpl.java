package com.example.healthdb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.healthdb.dao.OrdersDao;
import com.example.healthdb.exception.BusinessException;
import com.example.healthdb.exception.ErrorCode;
import com.example.healthdb.model.dto.OrdersAndEscortDTO;
import com.example.healthdb.model.dto.OrdersDTO;
import com.example.healthdb.model.entity.Escort;
import com.example.healthdb.model.entity.Orders;
import com.example.healthdb.model.request.AddOrdersRequest;
import com.example.healthdb.model.request.DeleteOrdersRequest;
import com.example.healthdb.model.request.UpdateOrdersRequest;
import com.example.healthdb.service.*;
import com.example.healthdb.utils.SnowFlakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    @Resource
    private PatientService patientService;

    @Resource
    private UserService userService;

    @Resource
    private HospitalService hospitalService;

    @Resource
    private OrdersAndEscortService ordersAndEscortService;

    @Resource
    private EscortService escortService;

    @Resource
    private OrdersDao ordersDao;



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
    public List<OrdersDTO> queryByIsFinished(Integer isFinished, Integer uid) {

        LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Orders::getIsFinished,isFinished)
                .eq(Orders::getIsDelete,0)
                .eq(Orders::getUid,uid);

        List<Orders> ordersList = ordersDao.selectList(lambdaQueryWrapper);
        List<OrdersDTO> ordersDTOList = new ArrayList<>();
        for (Orders orders : ordersList){
            OrdersDTO ordersDTO = new OrdersDTO();
            BeanUtils.copyProperties(orders,ordersDTO);
            ordersDTO.setPname(patientService.getById(orders.getPid()).getName());
            ordersDTO.setHname(hospitalService.getByID(orders.getHid()).getName());
            ordersDTOList.add(ordersDTO);
        }

        return ordersDTOList;

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

    /**
     * 超时自动完成
     */
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

    /**
     * 根据订单id查询订单
     * @param id
     * @return
     */
    @Override
    public OrdersAndEscortDTO queryById(Integer id) {
        Orders orders = getById(id);
        OrdersAndEscortDTO ordersAndEscortDTO = new OrdersAndEscortDTO();

        ordersAndEscortDTO.setIsFinished(orders.getIsFinished());
        ordersAndEscortDTO.setUpdateTime(orders.getUpdateTime());
        ordersAndEscortDTO.setEname(userService.getById(escortService.getById(ordersAndEscortService.queryByOid(orders.getId()).getEid()).getUid()).getRealname());
        //TODO 根据订单查询服务类型
        //ordersDTO.setServerType(orders.get);
        ordersAndEscortDTO.setPname(patientService.getById(orders.getPid()).getName());
        ordersAndEscortDTO.setGender(patientService.getById(orders.getPid()).getGender());
        ordersAndEscortDTO.setAge(patientService.getById(orders.getPid()).getAge());
        ordersAndEscortDTO.setTelephoneNumber(patientService.getById(orders.getPid()).getTelephoneNumber());
        ordersAndEscortDTO.setRelationship(patientService.getById(orders.getPid()).getRelationship());
        ordersAndEscortDTO.setStartTime(orders.getStartTime());
        ordersAndEscortDTO.setHname(hospitalService.getByID(orders.getHid()).getName());
        ordersAndEscortDTO.setOid(orders.getId());
        ordersAndEscortDTO.setRequirement(orders.getRequirement());
        return ordersAndEscortDTO;
    }

    /**
     * 查询陪诊师可接单订单
     * @param uid
     * @return
     */
    @Override
    public List<OrdersDTO> queryAvailableOrders(Integer uid) {
        LambdaQueryWrapper<Escort> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Escort::getUid,uid);
        Escort escort = escortService.getOne(lambdaQueryWrapper);
        LambdaQueryWrapper<Orders> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(Orders::getAreaCode,escort.getAreaCode())
                .eq(Orders::getIsFinished,0)
                .eq(Orders::getIsDelete,0);

        List<Orders> ordersList = ordersDao.selectList(lambdaQueryWrapper1);
        List<OrdersDTO> ordersDTOList = new ArrayList<>();

        for (Orders orders : ordersList){
            OrdersDTO ordersDTO = new OrdersDTO();
            BeanUtils.copyProperties(orders,ordersDTO);
            ordersDTO.setPname(patientService.getById(orders.getPid()).getName());
            ordersDTO.setHname(hospitalService.getByID(orders.getHid()).getName());
            ordersDTOList.add(ordersDTO);
        }
        return ordersDTOList;
    }


}
