package com.example.healthdb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.healthdb.dao.UserDao;
import com.example.healthdb.model.dto.UserDTO;
import com.example.healthdb.model.entity.Hospital;
import com.example.healthdb.model.entity.User;
import com.example.healthdb.model.request.IdentityRequest;
import com.example.healthdb.model.request.LoginRequest;
import com.example.healthdb.model.request.UpdateAvatarRequest;
import com.example.healthdb.model.request.UpdateOtherRequest;
import com.example.healthdb.model.vo.LoginVo;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends IService<User> {

    /**
     * 用户登录
     * @param loginRequest
     * @return
     */
    LoginVo login(LoginRequest loginRequest);

    /**
     * 用户注册
     * @param loginRequest
     */
    void register(LoginRequest loginRequest);

    /**
     * 更新用户头像
     */
    void updateAvatar(UpdateAvatarRequest avatarRequest);

    /**
     * 更新除用户头像和身份证信息外的信息
     * @param request
     */
    void updateInformation(UpdateOtherRequest request);

    /**
     * 身份证和姓名检验
     */
    void identify(IdentityRequest request) throws Exception;

    /**
     * 通过token获取个人信息
     * @return
     */
    UserDTO getInformation(HttpServletRequest request) throws Exception;

    /**
     * 把user类型转换成userdto类型
     * @param user
     * @return
     */
    UserDTO changeFromUserToDto(User user) throws Exception;
}
