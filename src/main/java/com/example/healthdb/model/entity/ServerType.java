package com.example.healthdb.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.healthdb.common.BaseEntity;
import lombok.Data;

@Data
@TableName("server_type")
public class ServerType extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;

    private String name;

    private Integer limit;

    private Float money;
}
