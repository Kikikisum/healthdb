package com.example.healthdb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.healthdb.model.entity.Patient;
import com.example.healthdb.model.request.AddPatientRequest;
import com.example.healthdb.model.request.DeletePatientRequest;

import java.util.List;

public interface PatientService extends IService<Patient> {

    /**
     * 增加一个陪诊人
     * @param request
     */
    Integer addPatient(AddPatientRequest request);

    /**
     * 软删除一个陪诊人
     * @param request
     */
    void deletePatient(DeletePatientRequest request);

    /**
     * 查询用户的所有陪诊人
     * @param id
     * @return
     */
    List<Patient> queryByMy(Long id);
}
