package com.example.healthdb.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.healthdb.common.BaseEntity;
import lombok.Data;

@Data
@TableName("user")
public class User extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;

    private String avatar;

    private String nickname;

    private String realname;

    @TableField("id_number")
    private String idNumber;

    private String password;

    private Integer status;

    private String telephone;

    @TableField("money")
    private Float money;
}
