package com.example.healthdb.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author xwb
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("orders")
public class Orders extends BaseEntity{
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

    @TableField("is_finished")
    private Integer isFinished;
}
