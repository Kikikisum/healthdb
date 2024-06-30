package com.example.healthdb.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.healthdb.common.BaseEntity;
import lombok.Data;

@TableName("hospital_server_type")
@Data
public class HospitalServerType extends BaseEntity {
    private Integer id;
    private Integer hid;
    private Integer sid;
    private Integer sold_count;
}
