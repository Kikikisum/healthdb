package com.example.healthdb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.healthdb.dao.EscortDao;
import com.example.healthdb.dao.OrdersAndEscortDao;
import com.example.healthdb.dao.OrdersDao;
import com.example.healthdb.exception.BusinessException;
import com.example.healthdb.exception.ErrorCode;

import com.example.healthdb.model.dto.OrdersAndEscortDTO;
import com.example.healthdb.model.entity.Escort;
import com.example.healthdb.model.entity.Orders;
import com.example.healthdb.model.entity.OrdersAndEscort;
import com.example.healthdb.model.request.AddOrderAndEscortRequest;
import com.example.healthdb.model.request.DeleteOrdersAndEscortRequest;

import com.example.healthdb.service.*;
import com.example.healthdb.utils.SnowFlakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Resource
    private EscortDao escortDao;

    @Resource
    private OrdersAndEscortDao ordersAndEscortDao;

    @Resource
    private OrdersDao ordersDao;


    @Resource
    private UserService userService;

    @Resource
    private ServerTypeService serverTypeService;

    @Resource
    private PatientService patientService;

    @Resource
    private HospitalService hospitalService;


    /**
     * 陪诊师接单
     * @param request
     */
    @Override
    public void addOrdersAndEscort(AddOrderAndEscortRequest request) {
        OrdersAndEscort ordersAndEscort = new OrdersAndEscort();
        if(queryByOid(request.getOid()) != null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = snowFlakeUtils.nextId();
        ordersAndEscort.setId(Math.abs(id.intValue()));
        // 根据用户id得到陪诊师id
        LambdaQueryWrapper<Escort> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Escort::getUid,request.getUid());
        Escort escort = escortDao.selectOne(lambdaQueryWrapper);
        System.out.println(escort);
        ordersAndEscort.setEid(escort.getId());
        ordersAndEscort.setOid(request.getOid());
        LambdaQueryWrapper<Orders> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(Orders::getId,request.getOid());
        Orders orders = ordersDao.selectOne(lambdaQueryWrapper1);
        orders.setStatus(1);
        ordersDao.updateById(orders);
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


    /**
     * 根据陪诊师订单完成状况查询订单
     * @param status
     * @param uid
     * @return
     */
    @Override
    public List<OrdersAndEscortDTO> queryByStatus(Integer status, Integer uid) {
        LambdaQueryWrapper<Escort> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Escort::getUid,uid);

        Escort escort = escortDao.selectOne(lambdaQueryWrapper);
        if(escort == null){
            throw new BusinessException(ErrorCode.NOT_ESCORT);
        }

        LambdaQueryWrapper<OrdersAndEscort> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(OrdersAndEscort::getEid,escort.getId());

        List<OrdersAndEscort> ordersAndEscortList = ordersAndEscortDao.selectList(lambdaQueryWrapper1);

        if(ordersAndEscortList.isEmpty()){
            return null;
        }
        List<Integer> oids = new ArrayList<>();
        for(OrdersAndEscort ordersAndEscort : ordersAndEscortList){
            oids.add(ordersAndEscort.getOid());
        }

        LambdaQueryWrapper<Orders> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
        if(status == 4){
            lambdaQueryWrapper2.in(Orders::getId,oids);
        }else {
            lambdaQueryWrapper2.in(Orders::getId,oids)
                    .eq(Orders::getStatus,status);
        }

        List<Orders> ordersList = ordersDao.selectList(lambdaQueryWrapper2);
        List<OrdersAndEscortDTO> ordersAndEscortDTOS = new ArrayList<>();

        for (Orders orders : ordersList){
            OrdersAndEscortDTO ordersAndEscortDTO = new OrdersAndEscortDTO();
            ordersAndEscortDTO.setStatus(orders.getStatus());
            ordersAndEscortDTO.setUpdateTime(orders.getUpdateTime());

            if(queryByOid(orders.getId()) != null){
                ordersAndEscortDTO.setEname(userService.getById(escortService.getById(queryByOid(orders.getId()).getEid()).getUid()).getRealname());
                ordersAndEscortDTO.setEid(escortService.getById(queryByOid(orders.getId()).getEid()).getId());
            }
            ordersAndEscortDTO.setMoney(serverTypeService.getById(orders.getSid()).getMoney());
            ordersAndEscortDTO.setServerType(serverTypeService.getById(orders.getSid()).getName());
            ordersAndEscortDTO.setPname(patientService.getById(orders.getPid()).getName());
            ordersAndEscortDTO.setGender(patientService.getById(orders.getPid()).getGender());
            ordersAndEscortDTO.setAge(patientService.getById(orders.getPid()).getAge());
            ordersAndEscortDTO.setTelephoneNumber(patientService.getById(orders.getPid()).getTelephoneNumber());
            ordersAndEscortDTO.setRelationship(patientService.getById(orders.getPid()).getRelationship());
            ordersAndEscortDTO.setStartTime(orders.getStartTime());
            ordersAndEscortDTO.setEndTime(orders.getEndTime());
            ordersAndEscortDTO.setHname(hospitalService.getByID(orders.getHid()).getName());
            ordersAndEscortDTO.setOid(orders.getId());
            ordersAndEscortDTO.setRequirement(orders.getRequirement());
            ordersAndEscortDTOS.add(ordersAndEscortDTO);
        }
        return ordersAndEscortDTOS;

    }

}
