package com.WHY.lease.web.app.service.impl;

import com.WHY.lease.common.login.LoginUserHolder;
import com.WHY.lease.model.entity.GraphInfo;
import com.WHY.lease.model.entity.WorryInfo;
import com.WHY.lease.model.enums.ItemType;
import com.WHY.lease.web.app.mapper.WorryMapper;
import com.WHY.lease.web.app.service.GraphInfoService;
import com.WHY.lease.web.app.service.WorryService;
import com.WHY.lease.web.app.vo.graph.GraphVo;
import com.WHY.lease.web.app.vo.message.MessageOutVo;
import com.WHY.lease.web.app.vo.message.WorryInfoVo;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorryServiceImpl extends ServiceImpl<WorryMapper, WorryInfo> implements WorryService {

    @Autowired
    private GraphInfoService graphInfoService;
    @Autowired
    private WorryMapper mapper;
    @Override
    public void saveOrUpdateWorry(MessageOutVo message) {
        message.setUserid(LoginUserHolder.getLoginUser().getUserId());
        WorryInfo worryInfo=new WorryInfo();
        worryInfo.setUserid(message.getUserid());
        worryInfo.setId(message.getId());
        worryInfo.setMessage(message.getMessage());
        worryInfo.setSubject(message.getSubject());
        worryInfo.setUpdateTime(message.getUpdateTime());
        worryInfo.setCreateTime(message.getCreateTime());
        worryInfo.setIsDeleted(message.getIsDeleted());
        super.saveOrUpdate(worryInfo);
        List<GraphVo> graphVoList = message.getGraphVoList();
        if(graphVoList!=null&&!graphVoList.isEmpty()) {
            ArrayList<GraphInfo> graphInfos = new ArrayList<>();
            for (GraphVo graphVo : graphVoList) {
                GraphInfo graphInfo = new GraphInfo();
                graphInfo.setItemType(ItemType.WORRY);
                graphInfo.setItemId(worryInfo.getId());
                graphInfo.setName(graphVo.getName());
                graphInfo.setUrl(graphVo.getUrl());
                graphInfos.add(graphInfo);
            }
            graphInfoService.saveBatch(graphInfos);
        }
    }

    @Override
    public IPage<WorryInfoVo> pageItem(Page<WorryInfoVo> page, Long userId) {
       return  mapper.pageItem(page,userId);
    }
}
