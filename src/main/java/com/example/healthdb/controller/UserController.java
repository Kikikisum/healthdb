package com.example.healthdb.controller;

import com.example.healthdb.entity.mew;
import com.example.healthdb.service.mewService;
import com.example.healthdb.utils.BaseResult;
import com.example.healthdb.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private mewService mewService;

    @GetMapping("/mew")
    public BaseResult<mew> getMew(String name)
    {
        return ResultUtils.success(mewService.getByName(name));
    }

}
