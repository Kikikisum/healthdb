package com.example.healthdb.model.request;

import lombok.Data;

@Data
public class AddEvaluationRequest{
    private Integer order_id;

    private Integer uid;

    private Integer starLevel;

    private Integer processLevel;

    private Integer serverLevel;

    private String content;
}
