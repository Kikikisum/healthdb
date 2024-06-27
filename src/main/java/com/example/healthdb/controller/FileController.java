package com.example.healthdb.controller;

import com.example.healthdb.common.BaseResponse;
import com.example.healthdb.model.dto.FileUploadDTO;
import com.example.healthdb.utils.OSSFileUtil;
import com.example.healthdb.utils.ResultUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {

    @PostMapping("/upload")
    public BaseResponse<FileUploadDTO> uploadFile(MultipartFile[] files)
    {
        return ResultUtils.success(new FileUploadDTO(OSSFileUtil.uploadFiles(files)));
    }
}
