package com.example.healthdb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.healthdb.model.entity.Passage;
import com.example.healthdb.model.request.AddPassageRequest;

import javax.servlet.http.HttpServletRequest;

public interface PassageService extends IService<Passage> {

    /**
     * 添加文章
     * @param httpServletRequest
     * @param request
     */
    void addPassage(HttpServletRequest httpServletRequest, AddPassageRequest request);

    /**
     * 删除文章
     * @param httpServletRequest
     * @param id
     */
    void deletePassage(HttpServletRequest httpServletRequest,Integer id);
}
