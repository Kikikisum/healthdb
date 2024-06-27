package com.example.healthdb.model.request;

import lombok.Data;

import java.util.Date;

@Data
public class MutipleQueryOrdersRequest {

    private Integer uid;

    private Date startTime;

    private Date endTime;

    /**
     * 就诊人名字
     */
    private String name;

    /**
     * 排序标识: 1-从近到远，0-从远到近
     */
    private Integer sort;
}
