package com.example.healthdb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.healthdb.model.entity.Evaluation;
import com.example.healthdb.model.request.AddEvaluationRequest;
import com.example.healthdb.model.request.DeleteEvaluationRequest;

import java.util.List;

public interface EvalutaionService extends IService<Evaluation> {

    /**
     * 添加对完成订单的评价
     * @param request
     */
    void addEvaluation(AddEvaluationRequest request);

    /**
     * 删除评价
     * @param request
     */
    void deleteEvaluation(DeleteEvaluationRequest request);

    /**
     * 查询用户自身所做出的评价
     * @param uid
     * @return
     */
    List<Evaluation> queryMyEvaluation(Integer uid);

    /**
     * 查询医院对应的评价
     * @param hid
     * @return
     */
    List<Evaluation> queryByHospital(Integer hid);
}
