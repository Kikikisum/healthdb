package com.example.healthdb.model.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Integer id;

    private String avatar;

    private String nickname;

    private String realname;

    private Integer gender;

    private Integer age;

    private String idNumber;

    private Integer status;

    private String telephone;

    private Float money;
}
