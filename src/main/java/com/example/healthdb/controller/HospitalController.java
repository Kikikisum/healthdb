package com.example.healthdb.controller;

import com.example.healthdb.common.BaseResponse;
import com.example.healthdb.model.dto.HospitalCreateResDTO;
import com.example.healthdb.model.entity.Hospital;
import com.example.healthdb.model.request.AddHospitalRequest;
import com.example.healthdb.service.HospitalService;
import com.example.healthdb.utils.ResultUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/hospital")
public class HospitalController {
    @Resource
    private HospitalService hospitalService;

    /**
     * 根据id查询医院的具体信息
     * @param id
     * @return
     */
    @GetMapping("/query/by/{id}")
    public BaseResponse<Hospital> queryById(@PathVariable Integer id)
    {
        return ResultUtils.success(hospitalService.getByID(id));
    }

    /**
     * 根据地区代码查询相关医院信息
     * @param code
     * @return
     */
    @GetMapping("/query/area/{code}")
    public BaseResponse<List<Hospital>> queryByAreaCode(@PathVariable Integer code)
    {
        return ResultUtils.success(hospitalService.getByAreaCode(code));
    }

    /**
     * 根据医院名称进行模糊查询
     * @param name
     * @return
     */
    @GetMapping("/query/name")
    public BaseResponse<List<Hospital>> queryByName(@RequestParam String name) {
        return ResultUtils.success(hospitalService.getByName(name));
    }

    @PostMapping("/add")
    public BaseResponse<Void> addHospital(@RequestBody AddHospitalRequest request)
    {
        hospitalService.addHospital(request);
        return ResultUtils.success(null);
    }

    /**
     * 批量创建医院
     * @param file
     * @return
     */
    @PostMapping("/excel/add")
    public BaseResponse<HospitalCreateResDTO> addByExcel(MultipartFile file)
    {
        return ResultUtils.success(hospitalService.addByExcel(file));
    }

}
