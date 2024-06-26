package com.example.healthdb.controller;

import com.example.healthdb.common.BaseResponse;
import com.example.healthdb.model.request.AudictEscortRequest;
import com.example.healthdb.service.EscortService;
import com.example.healthdb.utils.ResultUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private EscortService escortService;

    /**
     * 审核陪诊师
     * @param request
     * @return
     */
    @PostMapping("/audict")
    public BaseResponse<Void> audictEscort(@RequestBody AudictEscortRequest request)
    {

        return ResultUtils.success(null);
    }
}
