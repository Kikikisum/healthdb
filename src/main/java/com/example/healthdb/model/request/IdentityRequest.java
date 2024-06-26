package com.example.healthdb.model.request;

import lombok.Data;

@Data
public class IdentityRequest {
    private Integer id;
    private String name;
    private String identity;
}
