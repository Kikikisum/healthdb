package com.example.healthdb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.healthdb.dao.PassageDao;
import com.example.healthdb.exception.BusinessException;
import com.example.healthdb.exception.ErrorCode;
import com.example.healthdb.model.entity.Passage;
import com.example.healthdb.model.request.AddPassageRequest;
import com.example.healthdb.model.request.DeletePassageRequest;
import com.example.healthdb.service.PassageService;
import com.example.healthdb.utils.JwtUtils;
import com.example.healthdb.utils.SnowFlakeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class PassageServiceImpl extends ServiceImpl<PassageDao, Passage> implements PassageService {

    @Autowired
    SnowFlakeUtils snowFlakeUtils;

    @Override
    public void addPassage(HttpServletRequest httpServletRequest, AddPassageRequest request) {
        // 检查权限
        if (!JwtUtils.getRoleFromToken(httpServletRequest.getHeader("token")).equals(JwtUtils.ADMIN))
        {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        Passage passage = new Passage();
        passage.setCreateTime(new Date());
        passage.setUpdateTime(new Date());
        passage.setIsDelete(0);
        Long id = snowFlakeUtils.nextId();
        passage.setId(Math.abs(id.intValue()));
        passage.setTitle(request.getTitle());
        passage.setAvatar(request.getAvatar());
        passage.setPosition(request.getPosition());
        passage.setPhoto(request.getPhoto());
        passage.setContent(request.getContent());
        passage.setName(request.getName());
        save(passage);
    }

    @Override
    public void deletePassage(HttpServletRequest httpServletRequest, DeletePassageRequest request) {
        // 检查权限
        if (!JwtUtils.getRoleFromToken(httpServletRequest.getHeader("token")).equals(JwtUtils.ADMIN))
        {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        Passage passage=getById(request.getId());
        if (passage==null)
        {
            throw new BusinessException(ErrorCode.ID_WRONG);
        }
        passage.setIsDelete(1);
        passage.setUpdateTime(new Date());
        updateById(passage);
    }

    @Override
    public List<Passage> getAllNoDeletedPassage(HttpServletRequest request) {
        LambdaQueryWrapper<Passage> passageLambdaQueryWrapper=new LambdaQueryWrapper<>();
        passageLambdaQueryWrapper.eq(Passage::getIsDelete,0);
        return list(passageLambdaQueryWrapper);
    }
}
