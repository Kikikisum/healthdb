package com.example.healthdb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.healthdb.model.entity.Patient;

public interface patientService extends IService<Patient> {

    boolean addPatient();
}
