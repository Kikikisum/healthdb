package com.example.healthdb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import com.example.healthdb.utils.JwtUtils;
import com.example.healthdb.utils.PasswordUtil;
import com.example.healthdb.utils.SnowFlakeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

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
            throw new BusinessException(ErrorCode.ID_WRONG);
        }
        // 检查电话是否正确
        if (request.getTelephone().length()!=11)
        {
            throw new BusinessException(ErrorCode.TELEPHONE_WRONG);
        }
        if (user.getIdNumber()==null&&request.getIdentity()!=null)
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
        userService.updateById(user);
        // 陪诊师信息插入
        Escort escort = new Escort();
        Long id = snowFlakeUtils.nextId();
        escort.setId(Math.abs(id.intValue()));
        escort.setUid(request.getUid());
        escort.setTelephone(request.getTelephone());
        escort.setAvatar(request.getAvatar());
        escort.setIsPassed(0);
        escort.setWorkSection(request.getWorkSection());
        if (request.getIsMedicalWorker()==1)
        {
            escort.setIsMedicalWorker(request.getIsMedicalWorker());
        }
        escort.setAge(request.getAge());
        escort.setGender(request.getGender());
        escort.setCreateTime(new Date());
        escort.setIsDelete(0);
        escort.setUpdateTime(new Date());
        escort.setAreaCode(request.getArea_code());
        save(escort);
    }

    @Override
    public void audictEscort(AudictEscortRequest request,String token) {
        // 检查权限
        if (!JwtUtils.getRoleFromToken(token).equals(JwtUtils.ADMIN))
        {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        Escort escort=getById(request.getId());
        if (escort==null)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        escort.setIsPassed(request.getIsPassed());
        if (escort.getIsPassed()==1)
        {
            User user=userService.getById(escort.getUid());
            user.setStatus(2);
            userService.updateById(user);
        }
        updateById(escort);
    }

    @Override
    public List<Escort> queryAll(HttpServletRequest request) {
        // 检查权限
        if (!JwtUtils.getRoleFromToken(request.getHeader("token")).equals(JwtUtils.ADMIN))
        {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        LambdaQueryWrapper<Escort> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Escort::getIsPassed,0);
        return list(lambdaQueryWrapper);
    }

    @Override
    public Escort getEscortInformation(Integer uid) {
        LambdaQueryWrapper<Escort> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Escort::getUid,uid);
        return getOne(lambdaQueryWrapper);
    }
}
