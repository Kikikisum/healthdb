package com.example.healthdb.controller;

import com.example.healthdb.common.BaseResponse;
import com.example.healthdb.model.entity.Escort;
import com.example.healthdb.model.request.AudictEscortRequest;
import com.example.healthdb.service.EscortService;
import com.example.healthdb.utils.ResultUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public BaseResponse<Void> audictEscort(HttpServletRequest httpServletRequest, @RequestBody AudictEscortRequest request)
    {
        escortService.audictEscort(request,httpServletRequest.getHeader("token"));
        return ResultUtils.success(null);
    }

    /**
     * 查询所有未审核的陪诊师请求
     * @return
     */
    @GetMapping("/query/all")
    public BaseResponse<List<Escort>> queryAll(HttpServletRequest httpServletRequest)
    {
        return ResultUtils.success(escortService.queryAll(httpServletRequest));
    }
}
