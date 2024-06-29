package com.example.healthdb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.healthdb.model.entity.ServerType;
import com.example.healthdb.model.request.AddServerTypeRequest;
import com.example.healthdb.model.request.DeleteServerTypeRequest;

import java.util.List;

public interface ServerTypeService extends IService<ServerType> {
    /**
     * 添加服务类型
     * @param request
     */
    void addServerType(AddServerTypeRequest request);

    /**
     * 根据id删除服务类型
     * @param request
     */
    void deleteServerType(DeleteServerTypeRequest request);

    /**
     * 根据id查询服务类型
     * @param id
     * @return
     */
    ServerType queryById(Integer id);


}
