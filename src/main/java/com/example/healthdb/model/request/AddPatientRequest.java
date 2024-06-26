package com.example.healthdb.model.request;

import lombok.Data;

@Data
public class AddPatientRequest {
    private Integer uid;

    private String name;

    private Integer gender;

    private Integer age;

    private String telephone;

    private String relationship;
}