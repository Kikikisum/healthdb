package com.example.healthdb.model.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import org.apache.poi.ss.usermodel.PictureData;


@Data
public class HospitalCreateDTO {
    @ExcelProperty("医院名称")
    private String name;

    @ExcelProperty("医院等级")
    private Integer hospitalLevel;

    @ExcelProperty("医院类型")
    private String hospitalType;

    @ExcelProperty("医院介绍")
    private String introduction;

    @ExcelProperty("地区代码")
    private Integer areaCode;

    @ExcelProperty("详细地址")
    private String detailAddress;

    @ExcelIgnore
    private String photoUrl;

    /**
     * 图片数据，临时存储
     */
    @ExcelIgnore
    private PictureData pictureData;
}
