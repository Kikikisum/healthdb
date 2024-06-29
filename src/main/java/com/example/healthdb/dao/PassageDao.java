package com.example.healthdb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.healthdb.model.entity.Passage;
import org.springframework.stereotype.Repository;

@Repository
public interface PassageDao extends BaseMapper<Passage> {
}
