package com.example.healthdb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.healthdb.dao.PatientDao;
import com.example.healthdb.exception.BusinessException;
import com.example.healthdb.exception.ErrorCode;
import com.example.healthdb.model.entity.Patient;
import com.example.healthdb.model.request.AddPatientRequest;
import com.example.healthdb.model.request.DeletePatientRequest;
import com.example.healthdb.service.PatientService;
import com.example.healthdb.utils.SnowFlakeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class PatientServiceImpl extends ServiceImpl<PatientDao, Patient> implements PatientService {

    @Resource
    SnowFlakeUtils snowFlakeUtils;

    @Override
    public void addPatient(AddPatientRequest request) {
        Patient patient = new Patient();
        Long id = snowFlakeUtils.nextId();
        patient.setId(Math.abs(id.intValue()));
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
    public void deletePatient(DeletePatientRequest request) {
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
