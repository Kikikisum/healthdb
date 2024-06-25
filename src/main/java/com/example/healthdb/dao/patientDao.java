package com.example.healthdb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.healthdb.model.entity.Patient;
import org.springframework.stereotype.Repository;

@Repository
public interface patientDao extends BaseMapper<Patient> {
}
