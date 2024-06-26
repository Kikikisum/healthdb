package com.example.healthdb.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Evaluation extends BaseEntity{
    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;

    private Integer hid;

    @TableField("star_level")
    private Integer starLevel;

    @TableField("process_level")
    private Integer processLevel;

    @TableField("sever_level")
    private Integer serverLevel;

    private String content;
}
