package com.example.healthdb.controller;

import com.example.healthdb.common.BaseResponse;
import com.example.healthdb.model.entity.ServerType;
import com.example.healthdb.model.request.AddServerTypeRequest;
import com.example.healthdb.model.request.DeleteServerTypeRequest;
import com.example.healthdb.service.ServerTypeService;
import com.example.healthdb.utils.ResultUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * @author xwb
 */

@RestController
@RequestMapping("/serverType")
public class ServerTypeController {

    @Resource
    private ServerTypeService serverTypeService;

    /**
     * 添加服务类型
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Void> add(@RequestBody AddServerTypeRequest request){
        serverTypeService.addServerType(request);
        return ResultUtils.success(null);
    }

    /**
     * 根据id删除服务类型
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Void> delete(@RequestBody DeleteServerTypeRequest request){
        serverTypeService.deleteServerType(request);
        return ResultUtils.success(null);
    }

    /**
     * 根据id查询订单
     * @param id
     * @return
     */
    @GetMapping("/query/by/{id}")
    public BaseResponse<ServerType> queryById(@PathVariable Long id){
        return  ResultUtils.success(serverTypeService.queryById(id));
    }


}
