package com.example.healthdb.controller;

import com.example.healthdb.common.BaseResponse;
import com.example.healthdb.model.entity.Evaluation;
import com.example.healthdb.model.request.AddEvaluationRequest;
import com.example.healthdb.model.request.DeleteEvaluationRequest;
import com.example.healthdb.service.EvalutaionService;
import com.example.healthdb.utils.ResultUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    @GetMapping("/query/all/{uid}")
    public BaseResponse<List<Evaluation>> queryAll(@PathVariable Integer uid)
    {
        return ResultUtils.success(evalutaionService.queryMyEvaluation(uid));
    }

    @GetMapping("/query/hospital/{hid}")
    public BaseResponse<List<Evaluation>> queryByHospitalId(@PathVariable Integer hid)
    {
        return ResultUtils.success(evalutaionService.queryMyEvaluation(hid));
    }
}
