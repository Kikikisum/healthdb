package com.example.healthdb.model.request;

import lombok.Data;


@Data
public class AddPassageRequest {
    private String title;

    private String avatar;

    private String name;

    private String position;

    private String photo;

    private String content;
}
