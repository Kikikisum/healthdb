package com.example.healthdb.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author xwb
 */
@Data
public class OrdersDTO {
    private Integer id;

    private Integer uid;

    private Integer hid;

    private Integer pid;

    private Integer sid;

    private String pname;

    private String hname;

    private String serverType;

    private Float money;

    private String relationship;

    @TableField("start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date startTime;

    @TableField("end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endTime;

    @TableField("telephone_number")
    private String telephoneNumber;

    private String requirement;

    @TableField("status")
    private Integer status;
}
