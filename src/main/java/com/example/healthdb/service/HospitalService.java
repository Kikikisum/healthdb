package com.example.healthdb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.healthdb.common.BaseResponse;
import com.example.healthdb.model.dto.HospitalCreateResDTO;
import com.example.healthdb.model.entity.Hospital;
import com.example.healthdb.model.request.AddHospitalRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HospitalService extends IService<Hospital> {

    /**
     * 根据id查询医院
     * @param id
     * @return
     */
    Hospital getByID(Integer id);

    /**
     * 根据地区代码查询医院
     * @param code
     * @return
     */
    List<Hospital> getByAreaCode(Integer code);

    /**
     * 根据医院名称进行模糊查询
     * @param name
     * @return
     */
    List<Hospital> getByName(String name);

    /**
     * 添加医院
     * @param request
     */
    void addHospital(AddHospitalRequest request);

    /**
     * 批量创建医院
     * @param file
     * @return
     */
    HospitalCreateResDTO addByExcel(MultipartFile file);

}
