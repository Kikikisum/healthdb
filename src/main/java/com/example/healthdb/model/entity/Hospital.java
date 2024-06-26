package com.example.healthdb.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("hospital")
public class Hospital extends BaseEntity{

    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;
    private String name;

    @TableField("hospital_level")
    private Integer hospitalLevel;

    @TableField("hospital_type")
    private String hospitalType;

    private String introduction;

    @TableField("area_code")
    private Integer areaCode;

    @TableField("detail_address")
    private String detailAddress;
}
