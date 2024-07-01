package com.example.healthdb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.healthdb.model.entity.Escort;
import com.example.healthdb.model.request.AddEscortRequest;
import com.example.healthdb.model.request.AudictEscortRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EscortService extends IService<Escort> {

    /**
     * 用户申请成为陪诊师
     * @param request
     */
    void addEscort(AddEscortRequest request);

    /**
     * 审核陪诊师申请
     * @param request
     */
    void audictEscort(AudictEscortRequest request,String token);

    /**
     * 查询所有未审核的陪诊师请求
     * @return
     */
    List<Escort> queryAll(HttpServletRequest request);

    /**
     * 获得指定id的陪诊师信息
     * @param eid
     * @return
     */
    Escort getEscortInformation(Integer eid);
}
