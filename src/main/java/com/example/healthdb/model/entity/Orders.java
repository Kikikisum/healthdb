package com.example.healthdb.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.healthdb.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author xwb
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("orders")
public class Orders extends BaseEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;

    private Integer uid;

    private Integer hid;

    private Integer pid;

    private Integer sid;

    @TableField("start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @TableField("end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @TableField("telephone_number")
    private String telephoneNumber;

    private String requirement;

    /**
     * 0-成功下单,1-被接单,2-进行中，3-已完成
     */
    @TableField("status")
    private Integer status;
}
