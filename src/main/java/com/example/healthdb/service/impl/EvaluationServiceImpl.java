package com.example.healthdb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.healthdb.config.SnowFlakeConfig;
import com.example.healthdb.dao.EvaluationDao;
import com.example.healthdb.exception.BusinessException;
import com.example.healthdb.exception.ErrorCode;
import com.example.healthdb.model.entity.Evaluation;
import com.example.healthdb.model.entity.Orders;
import com.example.healthdb.model.request.AddEvaluationRequest;
import com.example.healthdb.model.request.DeleteEvaluationRequest;
import com.example.healthdb.service.EvalutaionService;
import com.example.healthdb.service.OrdersService;
import com.example.healthdb.utils.SnowFlakeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EvaluationServiceImpl extends ServiceImpl<EvaluationDao, Evaluation> implements EvalutaionService {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private SnowFlakeUtils snowFlakeUtils;

    @Override
    public void addEvaluation(AddEvaluationRequest request) {
        Orders orders=ordersService.getById(request.getOrder_id());
        if (orders==null)
        {
            throw new BusinessException(ErrorCode.ID_WRONG);
        }
        // 检查订单是否已完成
        if (orders.getIsFinished()!=1)
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
    public List<Evaluation> queryMyEvaluation(Integer uid) {
        List<Evaluation> evaluations=new ArrayList<>();
        LambdaQueryWrapper<Orders> ordersLambdaQueryWrapper=new LambdaQueryWrapper<>();
        ordersLambdaQueryWrapper.eq(Orders::getUid,uid);
        ordersLambdaQueryWrapper.eq(Orders::getIsFinished,1);
        List<Orders> ordersList =ordersService.list(ordersLambdaQueryWrapper);
        for (Orders orders:ordersList)
        {
            LambdaQueryWrapper<Evaluation> evaluationLambdaQueryWrapper = new LambdaQueryWrapper<>();
            evaluationLambdaQueryWrapper.eq(Evaluation::getOid,orders.getId());
            Evaluation evaluation=getOne(evaluationLambdaQueryWrapper);
            if (evaluation!=null)
            {
                evaluations.add(evaluation);
            }
        }
        return evaluations;
    }

    @Override
    public List<Evaluation> queryByHospital(Integer hid) {
        List<Evaluation> evaluations=new ArrayList<>();
        LambdaQueryWrapper<Orders> ordersLambdaQueryWrapper=new LambdaQueryWrapper<>();
        ordersLambdaQueryWrapper.eq(Orders::getHid,hid);
        ordersLambdaQueryWrapper.eq(Orders::getIsFinished,1);
        List<Orders> ordersList =ordersService.list(ordersLambdaQueryWrapper);
        for (Orders orders:ordersList)
        {
            LambdaQueryWrapper<Evaluation> evaluationLambdaQueryWrapper = new LambdaQueryWrapper<>();
            evaluationLambdaQueryWrapper.eq(Evaluation::getOid,orders.getId());
            Evaluation evaluation=getOne(evaluationLambdaQueryWrapper);
            if (evaluation!=null)
            {
                evaluations.add(evaluation);
            }
        }
        return evaluations;
    }
}
