package com.example.healthdb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.healthdb.model.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseMapper<User> {
}
