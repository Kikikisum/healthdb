package com.example.healthdb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.healthdb.dao.UserDao;
import com.example.healthdb.exception.BusinessException;
import com.example.healthdb.exception.ErrorCode;
import com.example.healthdb.model.entity.User;
import com.example.healthdb.model.request.IdentityRequest;
import com.example.healthdb.model.request.LoginRequest;
import com.example.healthdb.model.request.UpdateAvatarRequest;
import com.example.healthdb.model.request.UpdateOtherRequest;
import com.example.healthdb.model.vo.LoginVo;
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

    @Resource
    SnowFlakeUtils snowFlakeUtils;

    @Override
    public LoginVo login(LoginRequest loginRequest) {
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
            LoginVo loginVo=new LoginVo(JwtUtils.getToken(map),user.getId(),is_companion);
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
    public void register(LoginRequest loginRequest) {
        // 检查密码规则，检查电话是否正确
        if (!PasswordUtil.checkPasswordRule(loginRequest.getPassword())||loginRequest.getTelephone().length()!=11)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getTelephone,loginRequest.getTelephone());
        User is = getOne(lambdaQueryWrapper);
        if (is!=null)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user=new User();
        Long userId = snowFlakeUtils.nextId();
        user.setId(userId.intValue());
        user.setPassword(PasswordUtil.getPassword(loginRequest.getPassword()));
        user.setStatus(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDelete(0);
        user.setTelephone(loginRequest.getTelephone());
        save(user);
    }

    @Override
    public void updateAvatar(UpdateAvatarRequest avatarRequest) {

    }

    @Override
    public void updateInformation(UpdateOtherRequest request) {
        User user=getById(request.getId());
        if (user!=null&&user.getStatus()!=1)
        {
            // 只更新不为空的内容
            if (request.getPassword()!=null&&!request.getPassword().isEmpty()&&request.getPassword().length()!=0&&
                    PasswordUtil.checkPasswordRule(request.getPassword()))
            {
                user.setPassword(PasswordUtil.getPassword(request.getPassword()));
            }
            if (request.getNickname()!=null&&!request.getNickname().isEmpty()&&request.getNickname().length()!=0)
            {
                user.setNickname(request.getNickname());
            }
            if (request.getAge()!=null)
            {
                user.setAge(request.getAge());
            }
            if (request.getGender()!=null)
            {
                user.setGender(request.getGender());
            }
            user.setUpdateTime(new Date());
            updateById(user);
        }
        else {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

    }


    @Override
    public void identify(IdentityRequest request) {
        if (IDNumberValidator.isValid(request.getIdentity()))
        {
            // 校验身份证信息
            User user = userDao.selectById(request.getId());
            if (user != null) {
                // 更新用户信息
                user.setRealname(request.getName());
                user.setIdNumber(request.getIdentity());
                user.setUpdateTime(new Date());
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
