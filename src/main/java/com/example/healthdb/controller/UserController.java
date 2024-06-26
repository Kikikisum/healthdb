package com.example.healthdb.controller;

import com.example.healthdb.model.dto.UserDTO;
import com.example.healthdb.model.request.IdentityRequest;
import com.example.healthdb.model.request.LoginRequest;
import com.example.healthdb.model.request.UpdateAvatarRequest;
import com.example.healthdb.model.request.UpdateOtherRequest;
import com.example.healthdb.model.vo.LoginVo;
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
    public BaseResponse<LoginVo> login(@RequestBody LoginRequest loginRequest)
    {
        return ResultUtils.success(userService.login(loginRequest));
    }

    @PostMapping("/register")
    public BaseResponse<Void> register(@RequestBody LoginRequest loginRequest)
    {
        userService.register(loginRequest);
        return ResultUtils.success(null);
    }

    @PostMapping("/update/avatar")
    public BaseResponse<Void> updateAvatar(@RequestBody UpdateAvatarRequest updateAvatarRequest)
    {
        userService.updateAvatar(updateAvatarRequest);
        return ResultUtils.success(null);
    }
    @PostMapping("/update/other")
    public BaseResponse<Void> updateInformation(@RequestBody UpdateOtherRequest request)
    {
        userService.updateInformation(request);
        return ResultUtils.success(null);
    }
    @PostMapping("/identify")
    public BaseResponse<Void> indentify(@RequestBody IdentityRequest request)
    {
        try {
            userService.identify(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResultUtils.success(null);
    }

    @GetMapping("/information")
    public BaseResponse<UserDTO> getInformation(HttpServletRequest request)
    {
        try {
            return ResultUtils.success(userService.getInformation(request));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
