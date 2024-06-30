package com.example.healthdb.model.request;

import lombok.Data;

@Data
public class DecreaseMoneyRequest {
    private Integer id;
    private Float money;
}
