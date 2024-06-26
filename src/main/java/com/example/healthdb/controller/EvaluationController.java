package com.example.healthdb.controller;

import com.example.healthdb.common.BaseResponse;
import com.example.healthdb.model.request.AddEvaluationRequest;
import com.example.healthdb.model.request.DeleteEvaluationRequest;
import com.example.healthdb.service.EvalutaionService;
import com.example.healthdb.utils.ResultUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/evaluate")
public class EvaluationController {

    @Resource
    private EvalutaionService evalutaionService;

    @PostMapping("/add")
    public BaseResponse<Void> addEvaluation(@RequestBody AddEvaluationRequest request)
    {
        evalutaionService.addEvaluation(request);
        return ResultUtils.success(null);
    }

    @PostMapping("/delete")
    public BaseResponse<Void> deleteEvaluation(@RequestBody DeleteEvaluationRequest request)
    {
        evalutaionService.deleteEvaluation(request);
        return ResultUtils.success(null);
    }
}
