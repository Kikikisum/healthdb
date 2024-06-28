package com.example.healthdb.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

/**
 * @author xwb
 */
@Data
public class OrdersAndEscortDTO {
    private String serverType;

    private String pname;

    private Integer gender;

    private Integer age;

    private String telephoneNumber;

    private String relationship;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date endTime;

    private String hname;

    private Integer oid;

    private String requirement;

    private Integer isFinished;

    private String ename;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
