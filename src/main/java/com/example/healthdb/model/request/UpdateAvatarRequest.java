package com.example.healthdb.model.request;

import lombok.Data;

@Data
public class UpdateAvatarRequest {
    private String avatar;
    private Integer uid;
}
