package com.example.healthdb.model.request;

import lombok.Data;

@Data
public class UpdateOtherRequest {
    private String nickname;
    private String password;
    private Integer gender;
    private Integer age;
    private Integer id;
}
