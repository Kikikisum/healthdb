package com.example.healthdb.controller;

import com.example.healthdb.common.BaseResponse;
import com.example.healthdb.model.request.AddEscortRequest;
import com.example.healthdb.service.EscortService;
import com.example.healthdb.utils.ResultUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/escort")
public class EscortController {

    @Resource
    private EscortService escortService;

    /**
     * 申请成为陪诊师
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Void> addEscort(@RequestBody AddEscortRequest request)
    {
        escortService.addEscort(request);
        return ResultUtils.success(null);
    }
}
