package com.example.healthdb.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.healthdb.common.BaseEntity;
import lombok.Data;

@Data
@TableName("passage")
public class Passage extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;

    private String title;

    private String name;

    private String avatar;

    private String position;

    private String photo;

    private String content;

}
