package com.example.healthdb.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class loginVo {
    private String token;

    private Integer id;

    private Integer isCompanion;
}
