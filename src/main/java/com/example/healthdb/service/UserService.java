package com.example.healthdb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.healthdb.model.entity.User;
import com.example.healthdb.model.request.identityRequest;
import com.example.healthdb.model.request.loginRequest;
import com.example.healthdb.model.request.updateAvatarRequest;
import com.example.healthdb.model.vo.loginVo;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends IService<User> {

    /**
     * 用户登录
     * @param loginRequest
     * @return
     */
    loginVo login(loginRequest loginRequest);

    /**
     * 用户注册
     * @param loginRequest
     */
    void register(loginRequest loginRequest);

    /**
     * 更新用户头像
     */
    void updateAvatar(updateAvatarRequest avatarRequest);


    void updateInformation();

    /**
     * 身份证和姓名检验
     */
    void identify(identityRequest request);

    /**
     * 通过token获取个人信息
     * @return
     */
    User getInformation(HttpServletRequest request);
}
