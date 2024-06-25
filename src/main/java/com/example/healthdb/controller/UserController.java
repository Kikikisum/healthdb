package com.example.healthdb.controller;

import com.example.healthdb.model.request.loginRequest;
import com.example.healthdb.model.vo.loginVo;
import com.example.healthdb.service.UserService;
import com.example.healthdb.utils.BaseResponse;
import com.example.healthdb.utils.BaseResult;
import com.example.healthdb.utils.ResultUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    public BaseResponse<loginVo> getMew(@RequestBody loginRequest loginRequest)
    {
        return ResultUtils.success(userService.login(loginRequest));
    }

    @PostMapping("/register")
    public BaseResponse<Void> register(@RequestBody loginRequest loginRequest)
    {
        userService.register(loginRequest);
        return ResultUtils.success(null);
    }


}
