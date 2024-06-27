package com.example.healthdb.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileUploadDTO {

    /**
     * 文件url，多个文件以<进行分隔
     */
    private String fileUrl;
}
