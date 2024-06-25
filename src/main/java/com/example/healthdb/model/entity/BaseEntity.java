package com.example.healthdb.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {
    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill= FieldFill.INSERT)
    protected Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time",fill= FieldFill.UPDATE)
    protected Date updateTime;

    /**
     * 删除标识: 1-已删除，0-未删除
     */
    @TableField(value ="is_deleted",fill = FieldFill.INSERT)
    protected Integer isDelete;
}
