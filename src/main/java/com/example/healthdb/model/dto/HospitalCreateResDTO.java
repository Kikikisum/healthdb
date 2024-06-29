package com.example.healthdb.model.dto;

import com.example.healthdb.model.entity.Hospital;
import com.example.healthdb.model.request.AddHospitalRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HospitalCreateResDTO {
    /**
     * 成功导入的医院个数
     */
    private Integer total;

    /**
     * 导入的医院信息
     */
    private List<HospitalCreateDTO> hospitalList;
}
