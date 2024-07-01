package com.example.healthdb.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.healthdb.common.BaseEntity;
import lombok.Data;

@TableName("hospital_server_type")
@Data
public class HospitalServerType extends BaseEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;
    private Integer hid;
    private Integer sid;
    private Integer sold_count;
}
