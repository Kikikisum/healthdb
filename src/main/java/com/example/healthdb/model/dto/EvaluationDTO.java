package com.example.healthdb.model.dto;

import lombok.Data;

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
}
