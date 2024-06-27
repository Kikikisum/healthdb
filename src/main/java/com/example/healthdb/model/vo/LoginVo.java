package com.example.healthdb.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginVo {
    private String token;

    private Integer id;

    private String nickname;

    private Integer isCompanion;

    /**
     * 是否实名
     */
    private Integer isIdentified;
}
