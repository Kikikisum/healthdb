package com.example.healthdb.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("server_type")
public class ServerType extends BaseEntity{
    private Integer id;
    private String name;
}
