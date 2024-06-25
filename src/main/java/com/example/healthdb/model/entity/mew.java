package com.example.healthdb.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("mew")
public class mew implements Serializable {
    private Integer age;
    private String name;
}
