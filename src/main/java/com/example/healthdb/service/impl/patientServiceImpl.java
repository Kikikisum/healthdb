package com.example.healthdb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.healthdb.dao.patientDao;
import com.example.healthdb.exception.BusinessException;
import com.example.healthdb.exception.ErrorCode;
import com.example.healthdb.model.entity.Patient;
import com.example.healthdb.model.request.addPatientRequest;
import com.example.healthdb.model.request.deletePatientRequest;
import com.example.healthdb.service.patientService;
import com.example.healthdb.utils.SnowFlakeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class patientServiceImpl extends ServiceImpl<patientDao, Patient> implements patientService {

    @Resource
    SnowFlakeUtils snowFlakeUtils;

    @Override
    public void addPatient(addPatientRequest request) {
        Patient patient = new Patient();
        Long id = snowFlakeUtils.nextId();
        patient.setId(id.intValue());
        patient.setUid(request.getUid());
        patient.setName(request.getName());
        patient.setGender(request.getGender());
        patient.setAge(request.getAge());
        patient.setTelephoneNumber(request.getTelephone());
        patient.setRelationship(request.getRelationship());
        patient.setCreateTime(new Date());
        patient.setUpdateTime(new Date());
        patient.setIsDelete(0);
        save(patient);
    }

    @Override
    public void deletePatient(deletePatientRequest request) {
        Patient patient = getById(request.getId());
        if (patient!=null)
        {
            patient.setUpdateTime(new Date());
            patient.setIsDelete(1);
            updateById(patient);
        }else {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }

    @Override
    public List<Patient> queryByMy(Long id) {
        LambdaQueryWrapper<Patient> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Patient::getUid,id);
        return list(lambdaQueryWrapper);
    }
}
