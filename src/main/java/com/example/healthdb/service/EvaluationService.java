package com.example.healthdb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.healthdb.model.dto.EvaluationDTO;
import com.example.healthdb.model.dto.OrdersAndEscortDTO;
import com.example.healthdb.model.entity.Evaluation;
import com.example.healthdb.model.request.AddEvaluationRequest;
import com.example.healthdb.model.request.DeleteEvaluationRequest;
import com.example.healthdb.model.request.QueryEvauationRequest;

import java.util.List;

public interface EvaluationService extends IService<Evaluation> {

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
    List<EvaluationDTO> queryMyEvaluation(Integer uid);

    /**
     * 查询医院对应的评价
     * @param hid
     * @return
     */
    List<EvaluationDTO> queryByHospital(Integer hid);

    /**
     * 添加用户名和用户头像字段
     * @param evaluation
     * @return
     */
    EvaluationDTO changeFromEvalutationToDTO(Evaluation evaluation);

    /**
     * 查询本人已评价的订单信息
     * @param uid
     * @return
     */
    List<OrdersAndEscortDTO> queryISEvaluation(Integer uid);
}
