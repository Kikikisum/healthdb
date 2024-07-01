package com.example.healthdb.model.dto;

import lombok.Data;

@Data
public class EscortDTO {
    private Integer id;

    private Integer uid;

    /**
     * 印象照片
     */
    private String avatar;

    private String telephone;

    private Integer isMedicalWorker;

    private String workSection;

    private Integer areaCode;

    private Integer isPassed;

    private Integer gender;

    private Integer age;

    private String ename;
}
