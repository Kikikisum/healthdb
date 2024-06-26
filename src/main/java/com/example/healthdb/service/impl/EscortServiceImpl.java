package com.example.healthdb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.healthdb.dao.EscortDao;
import com.example.healthdb.exception.BusinessException;
import com.example.healthdb.exception.ErrorCode;
import com.example.healthdb.model.entity.Escort;
import com.example.healthdb.model.entity.User;
import com.example.healthdb.model.request.AddEscortRequest;
import com.example.healthdb.model.request.AudictEscortRequest;
import com.example.healthdb.service.EscortService;
import com.example.healthdb.service.UserService;
import com.example.healthdb.utils.IDNumberValidator;
import com.example.healthdb.utils.PasswordUtil;
import com.example.healthdb.utils.SnowFlakeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class EscortServiceImpl extends ServiceImpl<EscortDao, Escort> implements EscortService {

    @Resource
    private UserService userService;

    @Resource
    private SnowFlakeUtils snowFlakeUtils;

    @Override
    public void addEscort(AddEscortRequest request) {
        User user = userService.getById(request.getUid());
        if (user==null)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 检查电话是否正确
        if (request.getTelephone().length()!=11)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (user.getIdNumber()==null)
        {
            // 校验身份证信息
            if (IDNumberValidator.isValid(request.getIdentity()))
            {
                // 更新用户信息
                user.setRealname(request.getName());
                user.setIdNumber(PasswordUtil.encrypt(request.getIdentity()));
                user.setRealname(request.getName());
                user.setUpdateTime(new Date());
            }
            else {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (request.getGender()!=null)
        {
            user.setGender(user.getGender());
        }
        if (request.getAge()!=null)
        {
            user.setAge(request.getAge());
        }
        userService.updateById(user);
        // 陪诊师信息插入
        Escort escort = new Escort();
        Long id = snowFlakeUtils.nextId();
        escort.setId(Math.abs(id.intValue()));
        escort.setUid(request.getUid());
        escort.setTelephone(request.getTelephone());
        escort.setAvatar(request.getAvatar());
        escort.setIsPass(0);
        escort.setWorkSection(request.getWorkSection());
        escort.setIsMedicalWorker(request.getIsMedicalWorker());
    }

    @Override
    public void audictEscort(AudictEscortRequest request) {
        
    }
}
