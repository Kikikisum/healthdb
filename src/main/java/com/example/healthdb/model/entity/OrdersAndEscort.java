package com.example.healthdb.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author xwb
 */
@Data
@TableName("orders_escort")
public class OrdersAndEscort extends BaseEntity{
    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;

    private Integer eid;

    private Integer oid;
}
