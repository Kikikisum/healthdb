package com.example.healthdb.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class EvaluationDTO {
    private Integer id;

    private Integer oid;

    private Integer starLevel;

    private Integer processLevel;

    private Integer serverLevel;

    private String content;

    private Integer uid;

    private String nickname;

    private String avatar;

    @JsonFormat(pattern = ("yyyy-MM-dd HH:mm:ss"))
    private Date createTime;
}
