package com.example.healthdb.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.EasyExcel;
import com.aliyun.oss.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.healthdb.dao.HospitalDao;
import com.example.healthdb.exception.BusinessException;
import com.example.healthdb.exception.ErrorCode;
import com.example.healthdb.model.dto.HospitalCreateDTO;
import com.example.healthdb.model.dto.HospitalCreateResDTO;
import com.example.healthdb.model.entity.Hospital;
import com.example.healthdb.model.request.AddHospitalRequest;
import com.example.healthdb.service.HospitalService;
import com.example.healthdb.utils.ByteArrayMultipartFile;
import com.example.healthdb.utils.HospitalListener;
import com.example.healthdb.utils.OSSFileUtil;
import com.example.healthdb.utils.SnowFlakeUtils;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class HospitalServiceImpl extends ServiceImpl<HospitalDao, Hospital> implements HospitalService{

    @Autowired
    private SnowFlakeUtils snowFlakeUtils;

    @Override
    public Hospital getByID(Integer id) {
        Hospital hospital=getById(id);
        if (hospital!=null)
        {
            return hospital;
        }else {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }

    @Override
    public List<Hospital> getByAreaCode(Integer code) {
        LambdaQueryWrapper<Hospital> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Hospital::getAreaCode,code);
        return list(lambdaQueryWrapper);
    }

    @Override
    public List<Hospital> getByName(String name) {
        LambdaQueryWrapper<Hospital> hospitalLambdaQueryWrapper=new LambdaQueryWrapper<>();
        hospitalLambdaQueryWrapper.like(Hospital::getName,name);
        return list(hospitalLambdaQueryWrapper);
    }

    @Override
    public void addHospital(AddHospitalRequest request) {
        Hospital hospital=new Hospital();
        Long id = snowFlakeUtils.nextId();
        hospital.setId(Math.abs(id.intValue()));
        hospital.setHospitalLevel(request.getHospitalLevel());
        hospital.setHospitalType(request.getHospitalType());
        hospital.setName(request.getName());
        hospital.setIntroduction(request.getIntroduction());
        hospital.setDetailAddress(request.getDetailAddress());
        hospital.setAreaCode(request.getAreaCode());
        hospital.setCreateTime(new Date());
        hospital.setUpdateTime(new Date());
        hospital.setIsDelete(0);
        save(hospital);
    }

    @Override
    public HospitalCreateResDTO addByExcel(MultipartFile file) {
        List<HospitalCreateDTO> readList;
        ExcelReader reader;
        try{
            readList=EasyExcel.read(file.getInputStream(),new HospitalListener())
                    .head(HospitalCreateDTO.class)
                    .sheet().doReadSync();
            reader = ExcelUtil.getReader(file.getInputStream());
        }catch (IOException e)
        {
            log.error("文件读取失败");
            throw new ClientException("读取文件失败");
        }
        List<XSSFPicture> pictures = StreamSupport.stream(reader.getSheet().getDrawingPatriarch().spliterator(),false)
                                    .filter(XSSFPicture.class::isInstance)
                                    .map(XSSFPicture.class::cast).toList();
        for (int i = 0; i < pictures.size(); i++) {
            XSSFPicture picture = pictures.get(i);
            PictureData pictureData = picture.getPictureData();
            readList.get(i).setPictureData(pictureData);
        }
        // 并行流上传图片，多线程加速
        readList.parallelStream().forEach(each -> {
            if (each.getPictureData() != null) {
                try {
                    // 获取文件扩展名
                    String fileExt = each.getPictureData().getMimeType().split(StrUtil.SLASH)[1];
                    // 创建临时的 MultipartFile 对象
                    MultipartFile multipartFile = new ByteArrayMultipartFile(each.getPictureData().getData(), "temp_file." + fileExt, fileExt);
                    // 上传文件到 OSS 并获取 URL
                    String url = OSSFileUtil.uploadFile(multipartFile);
                    // 设置上传后的 URL 到 each 对象的 photoUrl 属性中
                    each.setPhotoUrl(url);
                } catch (IOException e) {
                    log.error("上传文件时发生错误", e);
                    throw new RuntimeException("上传文件时发生错误");
                }
            }
        });

        List<HospitalCreateDTO> result = new ArrayList<>();
        for (HospitalCreateDTO hospitalCreateDTO:readList)
        {
            try {
                createHospital(hospitalCreateDTO);
                result.add(hospitalCreateDTO);
            }catch (Throwable e)
            {
                log.error("批量创建用户失败");
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        return HospitalCreateResDTO.builder()
                .total(result.size())
                .hospitalList(result)
                .build();
    }

    void createHospital(HospitalCreateDTO hospitalCreateDTO)
    {
        Hospital hospital=new Hospital();
        hospital.setIsDelete(0);
        hospital.setCreateTime(new Date());
        hospital.setUpdateTime(new Date());
        hospital.setName(hospitalCreateDTO.getName());
        hospital.setHospitalLevel(hospitalCreateDTO.getHospitalLevel());
        hospital.setHospitalType(hospitalCreateDTO.getHospitalType());
        hospital.setIntroduction(hospitalCreateDTO.getIntroduction());
        hospital.setAreaCode(hospitalCreateDTO.getAreaCode());
        hospital.setDetailAddress(hospitalCreateDTO.getDetailAddress());
        hospital.setPhoto(hospital.getPhoto());
        save(hospital);
    }
}
