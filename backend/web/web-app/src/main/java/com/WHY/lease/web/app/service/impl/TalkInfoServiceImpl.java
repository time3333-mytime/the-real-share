package com.WHY.lease.web.app.service.impl;

import com.WHY.lease.common.login.LoginUserHolder;
import com.WHY.lease.model.entity.GraphInfo;
import com.WHY.lease.model.entity.RoomInfo;
import com.WHY.lease.model.enums.ItemType;
import com.WHY.lease.web.app.mapper.MessageInfoMapper;
import com.WHY.lease.web.app.service.GraphInfoService;
import com.WHY.lease.web.app.service.RoomInfoService;
import com.WHY.lease.web.app.vo.graph.GraphVo;
import com.WHY.lease.web.app.vo.message.ContentInfoVo;
import com.WHY.lease.web.app.vo.message.TalkInfoVo;
import com.WHY.lease.model.entity.MessageInfo;
import com.WHY.lease.model.entity.talkInfo;
import com.WHY.lease.web.app.mapper.TalkInfoMapper;
import com.WHY.lease.web.app.service.TalkInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TalkInfoServiceImpl extends ServiceImpl<TalkInfoMapper, talkInfo>implements TalkInfoService {

    @Autowired
    private TalkInfoMapper mapper;

    @Autowired
    private GraphInfoService graphInfoService;
    @Autowired
    private RoomInfoService service;
    @Autowired
    private MessageInfoMapper messageInfoMapper;

    @Override
    public void saveContent(TalkInfoVo talkInfoVo) {
        talkInfoVo.setSenduserid(LoginUserHolder.getLoginUser().getUserId());
        MessageInfo messageInfo = messageInfoMapper.selectById(talkInfoVo.getMid());
        talkInfoVo.setUserid(messageInfo.getUserid());
        super.saveOrUpdate(talkInfoVo);
        List<GraphVo> graphVoList = talkInfoVo.getGraphVoList();
        if(graphVoList!=null&&!graphVoList.isEmpty()) {
            ArrayList<GraphInfo> graphInfos = new ArrayList<>();
            for (GraphVo graphVo : graphVoList) {
                GraphInfo graphInfo = new GraphInfo();
                graphInfo.setItemType(ItemType.CONTENT);
                graphInfo.setItemId(talkInfoVo.getId());
                graphInfo.setName(graphVo.getName());
                graphInfo.setUrl(graphVo.getUrl());
                graphInfos.add(graphInfo);
            }
            graphInfoService.saveBatch(graphInfos);
        }
    }

    @Override
    public IPage<ContentInfoVo> getRead(Page<ContentInfoVo> talkInfoVoPage, Long id) {
        return mapper.getRead(talkInfoVoPage,id);
    }

    @Override
    public void saveContent2(TalkInfoVo talkInfoVo) {
        talkInfoVo.setSenduserid(LoginUserHolder.getLoginUser().getUserId());
        RoomInfo byId = service.getById(talkInfoVo.getMid());
        talkInfoVo.setUserid(byId.getLandlordId());
        super.saveOrUpdate(talkInfoVo);
        List<GraphVo> graphVoList = talkInfoVo.getGraphVoList();
        if(graphVoList!=null&&!graphVoList.isEmpty()) {
            ArrayList<GraphInfo> graphInfos = new ArrayList<>();
            for (GraphVo graphVo : graphVoList) {
                GraphInfo graphInfo = new GraphInfo();
                graphInfo.setItemType(ItemType.CONTENT);
                graphInfo.setItemId(talkInfoVo.getId());
                graphInfo.setName(graphVo.getName());
                graphInfo.setUrl(graphVo.getUrl());
                graphInfos.add(graphInfo);
            }
            graphInfoService.saveBatch(graphInfos);
        }
    }

    @Override
    public IPage<ContentInfoVo> getRead2(Page<ContentInfoVo> contentInfoVoPage, Long userId) {
        return mapper.getRead2(contentInfoVoPage,userId);
    }
}

