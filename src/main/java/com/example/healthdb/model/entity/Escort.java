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

    @TableField("")
    private String telephone;

    @TableField("is_medical_work")
    private Integer isMedicalWorker;

    @TableField("work_section")
    private String workSection;

    @TableField("")
    private Integer isPass;

}
