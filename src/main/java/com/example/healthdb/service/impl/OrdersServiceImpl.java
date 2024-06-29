package com.example.healthdb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.healthdb.dao.OrdersDao;
import com.example.healthdb.exception.BusinessException;
import com.example.healthdb.exception.ErrorCode;
import com.example.healthdb.model.dto.OrdersAndEscortDTO;
import com.example.healthdb.model.entity.Escort;
import com.example.healthdb.model.entity.Hospital;
import com.example.healthdb.model.entity.Orders;
import com.example.healthdb.model.request.AddOrdersRequest;
import com.example.healthdb.model.request.DeleteOrdersRequest;
import com.example.healthdb.model.request.MutipleQueryOrdersRequest;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    @Resource
    private ServerTypeService serverTypeService;



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
            orders.setSid(orders.getSid());
            Date startTime = simpleDateFormat.parse(addOrdersRequest.getStartTime());
            orders.setStartTime(startTime);
            Date endTime = simpleDateFormat.parse(addOrdersRequest.getEndTime());
            orders.setEndTime(endTime);
            orders.setTelephoneNumber(addOrdersRequest.getTelephone());
            orders.setRequirement(addOrdersRequest.getRequirement());
            orders.setStatus(addOrdersRequest.getStatus());
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
     * @param status
     * @return
     */
    @Override
    public List<OrdersAndEscortDTO> queryByStatus(Integer status, Integer uid) {

        LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Orders::getStatus,status)
                .eq(Orders::getIsDelete,0)
                .eq(Orders::getUid,uid);

        List<Orders> ordersList = ordersDao.selectList(lambdaQueryWrapper);
        List<OrdersAndEscortDTO> ordersAndEscortDTOS = new ArrayList<>();
        for (Orders orders : ordersList){
            OrdersAndEscortDTO ordersAndEscortDTO = new OrdersAndEscortDTO();
            ordersAndEscortDTO.setStatus(orders.getStatus());
            ordersAndEscortDTO.setUpdateTime(orders.getUpdateTime());
            if(ordersAndEscortService.queryByOid(orders.getId()) != null){
                ordersAndEscortDTO.setEname(userService.getById(escortService.getById(ordersAndEscortService.queryByOid(orders.getId()).getEid()).getUid()).getRealname());
            }
            ordersAndEscortDTO.setServerType(serverTypeService.getById(orders.getSid()).getName());
            ordersAndEscortDTO.setPname(patientService.getById(orders.getPid()).getName());
            ordersAndEscortDTO.setGender(patientService.getById(orders.getPid()).getGender());
            ordersAndEscortDTO.setAge(patientService.getById(orders.getPid()).getAge());
            ordersAndEscortDTO.setTelephoneNumber(patientService.getById(orders.getPid()).getTelephoneNumber());
            ordersAndEscortDTO.setRelationship(patientService.getById(orders.getPid()).getRelationship());
            ordersAndEscortDTO.setStartTime(orders.getStartTime());
            ordersAndEscortDTO.setHname(hospitalService.getByID(orders.getHid()).getName());
            ordersAndEscortDTO.setOid(orders.getId());
            ordersAndEscortDTO.setRequirement(orders.getRequirement());
            ordersAndEscortDTOS.add(ordersAndEscortDTO);
        }

        return ordersAndEscortDTOS;

    }


    /**
     * 更新订单状态
     * @param request
     */
    @Override
    public void updateOrders(UpdateOrdersRequest request) {
        Orders orders = getById(request.getId());
        if (orders!=null)
        {
            orders.setUpdateTime(new Date());
            orders.setStatus(request.getStatus());
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
            order.setStatus(3);
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
        OrdersAndEscortDTO ordersAndEscortDTO = null;
        if(orders != null){
            ordersAndEscortDTO = new OrdersAndEscortDTO();
            ordersAndEscortDTO.setStatus(orders.getStatus());
            ordersAndEscortDTO.setUpdateTime(orders.getUpdateTime());
            if(ordersAndEscortService.queryByOid(orders.getId()) != null){
                ordersAndEscortDTO.setEname(userService.getById(escortService.getById(ordersAndEscortService.queryByOid(orders.getId()).getEid()).getUid()).getRealname());
            }
            ordersAndEscortDTO.setServerType(serverTypeService.getById(orders.getSid()).getName());
            ordersAndEscortDTO.setPname(patientService.getById(orders.getPid()).getName());
            ordersAndEscortDTO.setGender(patientService.getById(orders.getPid()).getGender());
            ordersAndEscortDTO.setAge(patientService.getById(orders.getPid()).getAge());
            ordersAndEscortDTO.setTelephoneNumber(patientService.getById(orders.getPid()).getTelephoneNumber());
            ordersAndEscortDTO.setRelationship(patientService.getById(orders.getPid()).getRelationship());
            ordersAndEscortDTO.setStartTime(orders.getStartTime());
            ordersAndEscortDTO.setHname(hospitalService.getByID(orders.getHid()).getName());
            ordersAndEscortDTO.setOid(orders.getId());
            ordersAndEscortDTO.setRequirement(orders.getRequirement());
        }

        return ordersAndEscortDTO;
    }

    /**
     * 查询陪诊师可接单订单
     * @param uid
     * @return
     */
    @Override
    public List<OrdersAndEscortDTO> queryAvailableOrders(Integer uid) {
        LambdaQueryWrapper<Escort> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Escort::getUid,uid);
        Escort escort = escortService.getOne(lambdaQueryWrapper);
        List<Hospital> hospitalList = hospitalService.getByAreaCode(escort.getAreaCode());

        List<Integer> hids = new ArrayList<>();
        hids.add(0);

        for (Hospital hospital : hospitalList){
            hids.add(hospital.getId());
        }

        LambdaQueryWrapper<Orders> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.in(Orders::getHid,hids)
                .eq(Orders::getStatus,0)
                .eq(Orders::getIsDelete,0);

        List<Orders> ordersList = ordersDao.selectList(lambdaQueryWrapper1);
        List<OrdersAndEscortDTO> ordersAndEscortDTOS = new ArrayList<>();

        for (Orders orders : ordersList){
            OrdersAndEscortDTO ordersAndEscortDTO = new OrdersAndEscortDTO();
            ordersAndEscortDTO.setStatus(orders.getStatus());
            ordersAndEscortDTO.setUpdateTime(orders.getUpdateTime());
            if(ordersAndEscortService.queryByOid(orders.getId()) != null){
                ordersAndEscortDTO.setEname(userService.getById(escortService.getById(ordersAndEscortService.queryByOid(orders.getId()).getEid()).getUid()).getRealname());
            }
            ordersAndEscortDTO.setServerType(serverTypeService.getById(orders.getSid()).getName());
            ordersAndEscortDTO.setPname(patientService.getById(orders.getPid()).getName());
            ordersAndEscortDTO.setGender(patientService.getById(orders.getPid()).getGender());
            ordersAndEscortDTO.setAge(patientService.getById(orders.getPid()).getAge());
            ordersAndEscortDTO.setTelephoneNumber(patientService.getById(orders.getPid()).getTelephoneNumber());
            ordersAndEscortDTO.setRelationship(patientService.getById(orders.getPid()).getRelationship());
            ordersAndEscortDTO.setStartTime(orders.getStartTime());
            ordersAndEscortDTO.setHname(hospitalService.getByID(orders.getHid()).getName());
            ordersAndEscortDTO.setOid(orders.getId());
            ordersAndEscortDTO.setRequirement(orders.getRequirement());
            ordersAndEscortDTOS.add(ordersAndEscortDTO);
        }
        return ordersAndEscortDTOS;
    }

    @Override
    public List<OrdersAndEscortDTO> queryByMutipleConditions(MutipleQueryOrdersRequest request) {
        LambdaQueryWrapper<Orders> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Orders::getUid,request.getUid());
        Date startTime = null;
        try {
            startTime = simpleDateFormat.parse(request.getStartTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Date endTime=null;
        try {
            endTime = simpleDateFormat.parse(request.getEndTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if (startTime!=null&&endTime!=null)
        {
            lambdaQueryWrapper.between(Orders::getStartTime,startTime,endTime);
            lambdaQueryWrapper.between(Orders::getEndTime,startTime,endTime);
        }
        if (request.getSort()!=null)
        {
            if (request.getSort()==1)
            {
                lambdaQueryWrapper.orderByDesc(Orders::getStartTime);
            }
            else {
                lambdaQueryWrapper.orderByAsc(Orders::getStartTime);
            }
        }
        List<Orders> ordersList=ordersDao.selectList(lambdaQueryWrapper);
        List<OrdersAndEscortDTO> ordersDTOList = new ArrayList<>();

        for (Orders orders : ordersList){
            OrdersAndEscortDTO ordersAndEscortDTO=new OrdersAndEscortDTO();
            BeanUtils.copyProperties(orders,ordersAndEscortDTO);
            ordersAndEscortDTO.setPname(patientService.getById(orders.getPid()).getName());
            ordersAndEscortDTO.setHname(hospitalService.getByID(orders.getHid()).getName());
            ordersAndEscortDTO.setEname(userService.getById(orders.getUid()).getRealname());
            ordersAndEscortDTO.setOid(orders.getId());
            // 查询陪诊师
            LambdaQueryWrapper<Escort> escortLambdaQueryWrapper=new LambdaQueryWrapper<>();
            escortLambdaQueryWrapper.eq(Escort::getUid,orders.getUid());
            Escort escort=escortService.getOne(escortLambdaQueryWrapper);
            if (escort!=null)
            {
                ordersAndEscortDTO.setGender(escort.getGender());
                ordersAndEscortDTO.setAge(escort.getAge());
            }
            ordersDTOList.add(ordersAndEscortDTO);
        }
        if (request.getName()!=null)
        {
            // 就诊人名字模糊查询
            Pattern p = Pattern.compile(".*"+request.getName()+".*");
            for (OrdersAndEscortDTO ordersDTO:ordersDTOList)
            {
                Matcher m = p.matcher(ordersDTO.getPname());
                if (!m.matches())
                {
                    ordersDTOList.remove(ordersDTO);
                }
            }
        }
        return ordersDTOList;
    }
}
