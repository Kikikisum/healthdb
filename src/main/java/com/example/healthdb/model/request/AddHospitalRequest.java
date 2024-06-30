package com.example.healthdb.model.request;

import lombok.Data;

@Data
public class AddHospitalRequest {

    private String name;

    private Integer hospitalLevel;

    private String hospitalType;

    private String introduction;

    private Integer areaCode;

    private String detailAddress;

    private String photo;
}
