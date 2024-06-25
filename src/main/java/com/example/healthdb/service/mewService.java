package com.example.healthdb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.healthdb.model.entity.mew;


public interface mewService extends IService<mew> {

    mew getByName(String name);
}
