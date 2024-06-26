package com.example.healthdb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.healthdb.dao.EvaluationDao;
import com.example.healthdb.model.entity.Evaluation;
import com.example.healthdb.model.entity.Orders;
import com.example.healthdb.model.request.AddEvaluationRequest;
import com.example.healthdb.model.request.DeleteEvaluationRequest;
import com.example.healthdb.service.EvalutaionService;
import com.example.healthdb.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluationServiceImpl extends ServiceImpl<EvaluationDao, Evaluation> implements EvalutaionService {

    @Autowired
    private OrdersService ordersService;

    @Override
    public void addEvaluation(AddEvaluationRequest request) {
        Orders orders=ordersService.getById(request.getOrder_id());
    }

    @Override
    public void deleteEvaluation(DeleteEvaluationRequest request) {

    }
}
