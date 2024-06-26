package com.example.healthdb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.healthdb.model.entity.Escort;
import com.example.healthdb.model.request.AddEscortRequest;
import com.example.healthdb.model.request.AudictEscortRequest;

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
    void audictEscort(AudictEscortRequest request);
}
