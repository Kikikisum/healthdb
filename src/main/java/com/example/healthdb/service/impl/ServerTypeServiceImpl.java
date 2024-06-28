package com.example.healthdb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.healthdb.dao.ServerTypeDao;
import com.example.healthdb.model.entity.ServerType;
import com.example.healthdb.service.ServerTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ServerTypeServiceImpl extends ServiceImpl<ServerTypeDao, ServerType> implements ServerTypeService {
}
