package com.example.healthdb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.healthdb.dao.ServerTypeDao;
import com.example.healthdb.exception.BusinessException;
import com.example.healthdb.exception.ErrorCode;

import com.example.healthdb.model.entity.ServerType;
import com.example.healthdb.model.request.AddServerTypeRequest;
import com.example.healthdb.model.request.DeleteServerTypeRequest;
import com.example.healthdb.service.ServerTypeService;
import com.example.healthdb.utils.SnowFlakeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


@Service
public class ServerTypeServiceImpl extends ServiceImpl<ServerTypeDao, ServerType> implements ServerTypeService {

    @Resource
    private SnowFlakeUtils snowFlakeUtils;

    @Resource
    private ServerTypeDao serverTypeDao;

    /**
     * 添加服务类型
     * @param request
     */
    @Override
    public void addServerType(AddServerTypeRequest request) {
        LambdaQueryWrapper<ServerType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ServerType::getName,request.getName());
        ServerType serverType = serverTypeDao.selectOne(lambdaQueryWrapper);
        if(serverType != null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        ServerType newServerType = new ServerType();
        Long id = snowFlakeUtils.nextId();
        newServerType.setId(Math.abs(id.intValue()));
        newServerType.setName(request.getName());
        newServerType.setCreateTime(new Date());
        newServerType.setUpdateTime(new Date());
        newServerType.setIsDelete(0);
        save(newServerType);
        // 将新的服务类型放入缓存
        cache.put(newServerType.getId(),newServerType);
    }


    /**
     * 根据id删除服务类型
     * @param request
     */
    @Override
    public void deleteServerType(DeleteServerTypeRequest request) {
        ServerType serverType = getById(request.getId());
        if (serverType != null) {
            serverType.setUpdateTime(new Date());
            serverType.setIsDelete(1);
            updateById(serverType);
            // 从缓存中删除
            cache.remove(serverType.getId());
        } else {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

    }


    /**
     * 根据id查询服务类型
     * @param id
     * @return
     */
    @Override
    public ServerType queryById(Integer id) {
        ServerType serverType=null;
        if (cache.get(id)!=null)
        {
            serverType=cache.get(id);
        }
        else {
            LambdaQueryWrapper<ServerType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(ServerType::getId,id);
            serverType = serverTypeDao.selectOne(lambdaQueryWrapper);
        }
        return serverType;
    }

}
