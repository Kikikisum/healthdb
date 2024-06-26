package com.example.healthdb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.healthdb.model.entity.Orders;
import org.springframework.stereotype.Repository;

/**
 * @author xwb
 */

@Repository
public interface OrdersDao extends BaseMapper<Orders> {
}