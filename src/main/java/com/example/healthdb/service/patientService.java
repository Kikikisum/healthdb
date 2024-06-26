package com.example.healthdb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.healthdb.model.entity.Patient;
import com.example.healthdb.model.request.addPatientRequest;
import com.example.healthdb.model.request.deletePatientRequest;

import java.util.List;

public interface patientService extends IService<Patient> {

    /**
     * 增加一个陪诊人
     * @param request
     */
    void addPatient(addPatientRequest request);

    /**
     * 软删除一个陪诊人
     * @param id
     */
    void deletePatient(deletePatientRequest request);

    /**
     * 查询用户的所有陪诊人
     * @param id
     * @return
     */
    List<Patient> queryByMy(Long id);
}
