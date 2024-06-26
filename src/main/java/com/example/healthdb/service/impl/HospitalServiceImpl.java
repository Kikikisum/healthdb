package com.example.healthdb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.healthdb.dao.HospitalDao;
import com.example.healthdb.exception.BusinessException;
import com.example.healthdb.exception.ErrorCode;
import com.example.healthdb.model.entity.Hospital;
import com.example.healthdb.model.request.AddHospitalRequest;
import com.example.healthdb.service.HospitalService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalServiceImpl extends ServiceImpl<HospitalDao, Hospital> implements HospitalService{

    @Override
    public Hospital getByID(Integer id) {
        Hospital hospital=getById(id);
        if (hospital!=null)
        {
            return hospital;
        }else {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }

    @Override
    public List<Hospital> getByAreaCode(Integer code) {
        LambdaQueryWrapper<Hospital> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Hospital::getAreaCode,code);
        return list(lambdaQueryWrapper);
    }

    @Override
    public List<Hospital> getByName(String name) {
        LambdaQueryWrapper<Hospital> hospitalLambdaQueryWrapper=new LambdaQueryWrapper<>();
        hospitalLambdaQueryWrapper.like(Hospital::getName,name);
        return list(hospitalLambdaQueryWrapper);
    }

    @Override
    public void addHospital(AddHospitalRequest request) {
        ;
    }
}
