package com.example.healthdb.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.informix.lang.Decimal;
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

    private Float money;

    private String hname;

    private Integer oid;

    private String requirement;

    private Integer status;

    private String ename;

    private Integer eid;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
