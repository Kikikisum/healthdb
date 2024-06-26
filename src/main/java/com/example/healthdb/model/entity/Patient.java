package com.example.healthdb.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * 就诊人
 */
@Data
@TableName("patient")
public class Patient extends BaseEntity{

    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;

    private Integer uid;

    private String name;

    private Integer gender;

    private Integer age;

    @TableField("telephone_number")
    private String telephoneNumber;

    private String relationship;

}
