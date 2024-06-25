package com.example.healthdb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.healthdb.dao.mewDao;
import com.example.healthdb.entity.mew;
import com.example.healthdb.service.mewService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class mewServiceImpl extends ServiceImpl<mewDao, mew> implements mewService {

    @Resource
    private mewDao dao;

    @Override
    public mew getByName(String name) {
        LambdaQueryWrapper<mew> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(mew::getName,name);
        return dao.selectOne(lambdaQueryWrapper);
    }
}
