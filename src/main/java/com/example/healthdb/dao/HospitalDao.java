package com.example.healthdb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.healthdb.model.entity.Hospital;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalDao extends BaseMapper<Hospital>{
}
