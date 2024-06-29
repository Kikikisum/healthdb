package com.example.healthdb.model.entity;

import lombok.Data;

@Data
public class Passage extends BaseEntity{

    private Integer id;

    private String title;

    private String avatar;

    private String position;

    private String photo;

    private String content;

}
