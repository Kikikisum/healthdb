package com.example.healthdb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.healthdb.dao.UserDao;
import com.example.healthdb.exception.BusinessException;
import com.example.healthdb.exception.ErrorCode;
import com.example.healthdb.model.entity.User;
import com.example.healthdb.model.request.identityRequest;
import com.example.healthdb.model.request.loginRequest;
import com.example.healthdb.model.request.updateAvatarRequest;
import com.example.healthdb.model.vo.loginVo;
import com.example.healthdb.service.UserService;
import com.example.healthdb.utils.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public loginVo login(loginRequest loginRequest) {
        // 先查询电话信息是否正确
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getTelephone,loginRequest.getTelephone());
        User user = userDao.selectOne(lambdaQueryWrapper);
        if (user==null)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 检查密码
        if (PasswordUtil.checkPassword(loginRequest.getPassword(),user.getPassword()))
        {
            // 登录成功
            Map<String,String> map =new HashMap<>();
            map.put("role", StatusUtils.changeFromStatusToRole(user.getStatus()));
            map.put("id",user.getId().toString());
            Integer is_companion = 0;
            if (user.getStatus() == 2)
            {
                is_companion=1;
            }
            loginVo loginVo=new loginVo(JwtUtils.getToken(map),user.getId(),is_companion);
            return loginVo;
        }
        else {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }

    /**
     * 通过手机号和密码注册
     * @param loginRequest
     */
    @Override
    public void register(loginRequest loginRequest) {
        // 检查密码规则，检查电话是否正确
        if (!PasswordUtil.checkPasswordRule(loginRequest.getPassword())||loginRequest.getTelephone().length()!=11)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        SnowFlakeUtils snowFlakeUtils=new SnowFlakeUtils(1,1,1);
        User user=new User();
        long userId = snowFlakeUtils.nextId();
        user.setId(userId);
        user.setPassword(PasswordUtil.getPassword(loginRequest.getPassword()));
        user.setStatus(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDelete(0);
        user.setTelephone(loginRequest.getTelephone());
        save(user);
    }

    @Override
    public void updateAvatar(updateAvatarRequest avatarRequest) {

    }

    @Override
    public void updateInformation() {

    }

    @Override
    public void identify(identityRequest request) {
        if (IDNumberValidator.isValid(request.getIdentity()))
        {
            // 校验身份证信息
            User user = userDao.selectById(request.getId());
            if (user != null) {
                // 更新用户信息
                user.setRealname(request.getName());
                user.setIdNumber(request.getIdentity());
                // 执行更新操作
                userDao.updateById(user);
            } else {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        else {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }

    @Override
    public User getInformation(HttpServletRequest request) {
        String token = request.getHeader("token");
        Integer userId = Integer.valueOf(JwtUtils.getIdFromToken(token));
        return userDao.selectById(userId);
    }
}
