package com.example.healthdb.model.request;

import lombok.Data;

@Data
public class AddEscortRequest {

    private Integer uid;

    /**
     * 印象照片
     */
    private String avatar;

    private String telephone;

    private Integer isMedicalWorker;

    private String workSection;

    private Integer age;

    private Integer gender;

    private String name;

    private String identity;
}
