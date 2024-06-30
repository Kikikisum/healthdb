package com.example.healthdb.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.healthdb.common.BaseEntity;
import lombok.Data;

@Data
public class Evaluation extends BaseEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;

    private Integer oid;

    @TableField("star_level")
    private Integer starLevel;

    @TableField("process_level")
    private Integer processLevel;

    @TableField("server_level")
    private Integer serverLevel;

    private String content;
}
