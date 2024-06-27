package com.example.healthdb.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Escort extends BaseEntity{

    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;

    private Integer uid;

    /**
     * 印象照片
     */
    private String avatar;

    @TableField("telephone_number")
    private String telephone;

    @TableField("is_medical_worker")
    private Integer isMedicalWorker;

    @TableField("work_section")
    private String workSection;

    @TableField("area_code")
    private Integer areaCode;

    @TableField("is_passed")
    private Integer isPassed;

    private Integer gender;

    private Integer age;

}
