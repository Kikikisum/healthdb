package com.example.healthdb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.healthdb.dao.HospitalServerTypeDao;
import com.example.healthdb.dao.ServerTypeDao;
import com.example.healthdb.model.dto.ServerTypeDTO;
import com.example.healthdb.model.entity.HospitalServerType;
import com.example.healthdb.model.entity.ServerType;
import com.example.healthdb.service.HospitalServerTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class HospitalServerTypeServiceImpl extends ServiceImpl<HospitalServerTypeDao,HospitalServerType> implements HospitalServerTypeService {

    @Resource
    private HospitalServerTypeDao hospitalServerTypeDao;

    @Resource
    private ServerTypeDao serverTypeDao;

    @Override
    public List<ServerTypeDTO> queryServerTypeByHid(Long hid) {
        LambdaQueryWrapper<HospitalServerType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(HospitalServerType::getHid,hid);

        List<HospitalServerType> hospitalServerTypeList = hospitalServerTypeDao.selectList(lambdaQueryWrapper);

        List<Integer> sids = new ArrayList<>();
        for(HospitalServerType hospitalServerType : hospitalServerTypeList){
            sids.add(hospitalServerType.getSid());
        }

        LambdaQueryWrapper<ServerType> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.in(ServerType::getId,sids);

        List<ServerType> serverTypeList = serverTypeDao.selectList(lambdaQueryWrapper1);
        List<ServerTypeDTO> serverTypeDTOList = new ArrayList<>();
        for(ServerType serverType : serverTypeList){
            ServerTypeDTO serverTypeDTO = new ServerTypeDTO();
            serverTypeDTO.setId(serverType.getId());
            serverTypeDTO.setName(serverType.getName());
            serverTypeDTO.setMoney(serverType.getMoney());
            serverTypeDTO.setLimit(serverType.getLimit());
           serverTypeDTOList.add(serverTypeDTO);
        }

        return serverTypeDTOList;
    }
}
