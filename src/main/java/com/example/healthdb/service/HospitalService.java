package com.example.healthdb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.healthdb.model.entity.Hospital;

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

}
