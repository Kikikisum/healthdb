package com.example.healthdb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.healthdb.model.entity.Passage;
import com.example.healthdb.model.request.AddPassageRequest;
import com.example.healthdb.model.request.DeletePassageRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
     * @param request
     */
    void deletePassage(HttpServletRequest httpServletRequest, DeletePassageRequest request);

    /**
     * 查询所有未删除的文章
     * @return
     */
    List<Passage> getAllNoDeletedPassage(HttpServletRequest request);
}
