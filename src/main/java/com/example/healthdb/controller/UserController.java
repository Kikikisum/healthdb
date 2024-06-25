package com.example.healthdb.controller;

import com.example.healthdb.model.entity.User;
import com.example.healthdb.model.request.identityRequest;
import com.example.healthdb.model.request.loginRequest;
import com.example.healthdb.model.request.updateAvatarRequest;
import com.example.healthdb.model.request.updateOtherRequest;
import com.example.healthdb.model.vo.loginVo;
import com.example.healthdb.service.UserService;
import com.example.healthdb.common.BaseResponse;
import com.example.healthdb.utils.ResultUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    public BaseResponse<loginVo> login(@RequestBody loginRequest loginRequest)
    {
        return ResultUtils.success(userService.login(loginRequest));
    }

    @PostMapping("/register")
    public BaseResponse<Void> register(@RequestBody loginRequest loginRequest)
    {
        userService.register(loginRequest);
        return ResultUtils.success(null);
    }

    @PostMapping("/update/avatar")
    public BaseResponse<Void> updateAvatar(@RequestBody updateAvatarRequest updateAvatarRequest)
    {
        userService.updateAvatar(updateAvatarRequest);
        return ResultUtils.success(null);
    }

    @PostMapping("/update/other")
    public BaseResponse<Void> updateInformation(@RequestBody updateOtherRequest request)
    {
        userService.updateInformation(request);
        return ResultUtils.success(null);
    }
    @PostMapping("/identify")
    public BaseResponse<Void> indentify(@RequestBody identityRequest request)
    {
        userService.identify(request);
        return ResultUtils.success(null);
    }

    @GetMapping("/information")
    public BaseResponse<User> getInformation(HttpServletRequest request)
    {
        return ResultUtils.success(userService.getInformation(request));
    }
}
