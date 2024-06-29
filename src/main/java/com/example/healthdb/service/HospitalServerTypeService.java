package com.example.healthdb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.healthdb.model.dto.ServerTypeDTO;
import com.example.healthdb.model.entity.HospitalServerType;

import java.util.List;

public interface HospitalServerTypeService extends IService<HospitalServerType> {

    /**
     * 根据id查询服务类型
     * @param hid
     * @return
     */
    List<ServerTypeDTO> queryServerTypeByHid(Long hid);
}
