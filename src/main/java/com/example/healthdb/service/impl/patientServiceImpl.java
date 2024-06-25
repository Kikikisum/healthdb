package com.example.healthdb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.healthdb.dao.patientDao;
import com.example.healthdb.model.entity.Patient;
import com.example.healthdb.service.patientService;
import org.springframework.stereotype.Service;

@Service
public class patientServiceImpl extends ServiceImpl<patientDao, Patient> implements patientService {
    @Override
    public boolean addPatient() {
        return false;
    }
}
