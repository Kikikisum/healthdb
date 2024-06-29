package com.example.healthdb.controller;

import com.example.healthdb.common.BaseResponse;
import com.example.healthdb.model.dto.ServerTypeDTO;
import com.example.healthdb.service.HospitalServerTypeService;
import com.example.healthdb.utils.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author xwb
 */
@RestController
@RequestMapping("/hospital_server")
public class HospitalServerTypeController {

    @Resource
    private HospitalServerTypeService hospitalServerTypeService;

    /**
     * 查询医院的服务类型
     * @param hid
     * @return
     */
    @GetMapping("/query/by/{hid}")
    public BaseResponse<List<ServerTypeDTO>> queryServerTypeByHid(@PathVariable Long hid){
        return ResultUtils.success(hospitalServerTypeService.queryServerTypeByHid(hid));
    }
}
