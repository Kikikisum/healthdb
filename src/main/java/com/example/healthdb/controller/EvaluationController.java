package com.example.healthdb.controller;

import com.example.healthdb.common.BaseResponse;
import com.example.healthdb.model.dto.EvaluationDTO;
import com.example.healthdb.model.dto.OrdersAndEscortDTO;
import com.example.healthdb.model.entity.Evaluation;
import com.example.healthdb.model.request.AddEvaluationRequest;
import com.example.healthdb.model.request.DeleteEvaluationRequest;
import com.example.healthdb.model.request.QueryEvauationRequest;
import com.example.healthdb.service.EvaluationService;
import com.example.healthdb.service.OrdersService;
import com.example.healthdb.utils.ResultUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/evaluate")
public class EvaluationController {

    @Resource
    private EvaluationService evalutaionService;

    @Resource
    private OrdersService ordersService;

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
    public BaseResponse<List<EvaluationDTO>> queryAll(@PathVariable Integer uid)
    {
        return ResultUtils.success(evalutaionService.queryMyEvaluation(uid));
    }

    @GetMapping("/query/hospital/{hid}")
    public BaseResponse<List<EvaluationDTO>> queryByHospitalId(@PathVariable Integer hid)
    {
        return ResultUtils.success(evalutaionService.queryByHospital(hid));
    }

    @PostMapping ("/query/orders")
    public BaseResponse<List<OrdersAndEscortDTO>> queryOrders(Integer uid)
    {
        return ResultUtils.success(evalutaionService.queryISEvaluation(uid));
    }
}
