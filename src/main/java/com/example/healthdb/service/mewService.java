package com.example.healthdb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.healthdb.entity.mew;
import org.springframework.stereotype.Service;


public interface mewService extends IService<mew> {

    mew getByName(String name);
}
