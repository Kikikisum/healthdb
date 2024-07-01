package com.example.healthdb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.healthdb.dao.EvaluationDao;
import com.example.healthdb.exception.BusinessException;
import com.example.healthdb.exception.ErrorCode;
import com.example.healthdb.model.dto.EvaluationDTO;
import com.example.healthdb.model.dto.OrdersAndEscortDTO;
import com.example.healthdb.model.entity.*;
import com.example.healthdb.model.request.AddEvaluationRequest;
import com.example.healthdb.model.request.DeleteEvaluationRequest;
import com.example.healthdb.model.request.QueryEvauationRequest;
import com.example.healthdb.service.*;
import com.example.healthdb.utils.SnowFlakeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EvaluationServiceImpl extends ServiceImpl<EvaluationDao, Evaluation> implements EvaluationService {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private SnowFlakeUtils snowFlakeUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private OrdersAndEscortService ordersAndEscortService;

    @Autowired
    private EscortService escortService;

    @Autowired
    private ServerTypeService serverTypeService;

    @Override
    public void addEvaluation(AddEvaluationRequest request) {
        Orders orders=ordersService.getById(request.getOrder_id());
        if (orders==null)
        {
            throw new BusinessException(ErrorCode.ID_WRONG);
        }
        // 检查订单是否已完成
        if (orders.getStatus() != 3)
        {
            throw new BusinessException(ErrorCode.ORDER_NOTFINISH);
        }
        // 比较用户和订单用户
        if (!orders.getUid().equals(request.getUid()))
        {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        //todo 重复评论的检查
        Evaluation evaluation = new Evaluation();
        Long id = snowFlakeUtils.nextId();
        evaluation.setId(Math.abs(id.intValue()));
        evaluation.setOid(request.getOrder_id());
        evaluation.setContent(request.getContent());
        evaluation.setStarLevel(request.getStarLevel());
        evaluation.setServerLevel(request.getServerLevel());
        evaluation.setProcessLevel(request.getProcessLevel());
        evaluation.setCreateTime(new Date());
        evaluation.setIsDelete(0);
        evaluation.setUpdateTime(new Date());
        save(evaluation);
    }

    @Override
    public void deleteEvaluation(DeleteEvaluationRequest request) {
        Evaluation evaluation=getById(request.getEvaluation_id());
        if (evaluation==null)
        {
            throw new BusinessException(ErrorCode.ID_WRONG);
        }
        // 比较用户和订单用户
        Orders orders=ordersService.getById(evaluation.getOid());
        if (!orders.getUid().equals(request.getUid()))
        {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        // 进行删除操作
        evaluation.setIsDelete(1);
        evaluation.setUpdateTime(new Date());
        updateById(evaluation);
    }

    @Override
    public List<EvaluationDTO> queryMyEvaluation(Integer uid) {
        List<EvaluationDTO> evaluationsDTO=new ArrayList<>();
        LambdaQueryWrapper<Orders> ordersLambdaQueryWrapper=new LambdaQueryWrapper<>();
        ordersLambdaQueryWrapper.eq(Orders::getUid,uid);
        ordersLambdaQueryWrapper.eq(Orders::getStatus,3);
        List<Orders> ordersList =ordersService.list(ordersLambdaQueryWrapper);
        for (Orders orders:ordersList)
        {
            LambdaQueryWrapper<Evaluation> evaluationLambdaQueryWrapper = new LambdaQueryWrapper<>();
            evaluationLambdaQueryWrapper.eq(Evaluation::getOid,orders.getId());
            Evaluation evaluation=getOne(evaluationLambdaQueryWrapper);
            if (evaluation!=null)
            {
                evaluationsDTO.add(changeFromEvalutationToDTO(evaluation));
            }
        }
        return evaluationsDTO;
    }

    @Override
    public List<EvaluationDTO> queryByHospital(Integer hid) {
        List<EvaluationDTO> evaluationsDTO=new ArrayList<>();
        LambdaQueryWrapper<Orders> ordersLambdaQueryWrapper=new LambdaQueryWrapper<>();
        ordersLambdaQueryWrapper.eq(Orders::getHid,hid);
        ordersLambdaQueryWrapper.eq(Orders::getStatus,3);
        List<Orders> ordersList =ordersService.list(ordersLambdaQueryWrapper);
        for (Orders orders:ordersList)
        {
            LambdaQueryWrapper<Evaluation> evaluationLambdaQueryWrapper = new LambdaQueryWrapper<>();
            evaluationLambdaQueryWrapper.eq(Evaluation::getOid,orders.getId());
            Evaluation evaluation=getOne(evaluationLambdaQueryWrapper);
            if (evaluation!=null)
            {
                evaluationsDTO.add(changeFromEvalutationToDTO(evaluation));
            }
        }
        return evaluationsDTO;
    }

    @Override
    public EvaluationDTO changeFromEvalutationToDTO(Evaluation evaluation) {
        EvaluationDTO evaluationDTO =new EvaluationDTO();
        BeanUtils.copyProperties(evaluation,evaluationDTO);
        User user = userService.getById(ordersService.getById(evaluation.getOid()).getUid());
        evaluationDTO.setAvatar(user.getAvatar());
        evaluationDTO.setNickname(user.getNickname());
        evaluationDTO.setUid(user.getId());
        evaluationDTO.setCreateTime(evaluation.getCreateTime());
        return evaluationDTO;
    }

    @Override
    public List<OrdersAndEscortDTO> queryISEvaluation(Integer uid) {
        List<OrdersAndEscortDTO> ordersDTOList = new ArrayList<>();
        LambdaQueryWrapper<Orders> ordersLambdaQueryWrapper=new LambdaQueryWrapper<>();
        ordersLambdaQueryWrapper.eq(Orders::getUid,uid);
        ordersLambdaQueryWrapper.eq(Orders::getStatus,3);
        List<Orders> ordersList =ordersService.list(ordersLambdaQueryWrapper);
        for (Orders orders:ordersList)
        {
            LambdaQueryWrapper<Evaluation> lambdaQueryWrapper=new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Evaluation::getOid,orders.getUid());
            Evaluation evaluation=getOne(lambdaQueryWrapper);
            if (evaluation!=null)
            {
                ordersList.remove(orders);
            }
            if (ordersList.isEmpty())
            {
                break;
            }
        }
        for (Orders orders : ordersList){
            OrdersAndEscortDTO ordersAndEscortDTO=new OrdersAndEscortDTO();
            BeanUtils.copyProperties(orders,ordersAndEscortDTO);
            ordersAndEscortDTO.setPname(patientService.getById(orders.getPid()).getName());
            ordersAndEscortDTO.setHname(hospitalService.getByID(orders.getHid()).getName());
            ordersAndEscortDTO.setRelationship(patientService.getById(orders.getPid()).getRelationship());
            ordersAndEscortDTO.setServerType(serverTypeService.queryById(orders.getSid()).getName());
            if(ordersAndEscortService.queryByOid(orders.getId()) != null){
                ordersAndEscortDTO.setEname(userService.getById(escortService.getById(ordersAndEscortService.queryByOid(orders.getId()).getEid()).getUid()).getRealname());
                ordersAndEscortDTO.setEid(escortService.getById(ordersAndEscortService.queryByOid(orders.getId()).getEid()).getId());
            }
            ordersAndEscortDTO.setEndTime(orders.getEndTime());
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
            ordersAndEscortDTO.setMoney(serverTypeService.getById(orders.getSid()).getMoney());
            ordersDTOList.add(ordersAndEscortDTO);
        }
        return ordersDTOList;
    }
}
