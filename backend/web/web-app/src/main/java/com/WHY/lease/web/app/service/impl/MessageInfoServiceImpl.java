package com.WHY.lease.web.app.service.impl;

import com.WHY.lease.common.login.LoginUserHolder;
import com.WHY.lease.model.entity.GraphInfo;
import com.WHY.lease.model.entity.UserInfo;
import com.WHY.lease.model.enums.ItemType;
import com.WHY.lease.web.app.mapper.GraphInfoMapper;
import com.WHY.lease.web.app.mapper.UserInfoMapper;
import com.WHY.lease.web.app.service.GraphInfoService;
import com.WHY.lease.web.app.vo.graph.GraphVo;
import com.WHY.lease.web.app.vo.message.TalkInfoVo;
import com.WHY.lease.model.entity.MessageInfo;
import com.WHY.lease.model.entity.talkInfo;
import com.WHY.lease.web.app.mapper.MessageInfoMapper;
import com.WHY.lease.web.app.mapper.TalkInfoMapper;
import com.WHY.lease.web.app.service.MessageInfoService;
import com.WHY.lease.web.app.vo.message.MessageDetailVo;
import com.WHY.lease.web.app.vo.message.MessageOutVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class MessageInfoServiceImpl extends ServiceImpl<MessageInfoMapper, MessageInfo> implements MessageInfoService {

    @Autowired
    private MessageInfoMapper mapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private GraphInfoMapper graphInfoMapper;
    @Autowired
    private TalkInfoMapper talkInfoMapper;

    @Autowired
    private GraphInfoService graphInfoService;
    @Override
    public IPage<MessageOutVo> pageItem(Page<MessageOutVo> messageItemVoPage) {
        return mapper.pageItem(messageItemVoPage);
    }

    @Override
    public MessageDetailVo getDetailById(Long id) {
        MessageInfo messageInfo=mapper.selectById(id);
        if (messageInfo == null) {
            return null;
        }
        List<GraphVo> graphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.MESSAGE, id);
        LambdaQueryWrapper<talkInfo> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(talkInfo::getMid,id);
        List<talkInfo> List =talkInfoMapper.selectList(lambdaQueryWrapper);
        List<TalkInfoVo>List1=new ArrayList<>();

        for (talkInfo talkInfo : List) {
            TalkInfoVo talkInfoVo=new TalkInfoVo();
            BeanUtils.copyProperties(talkInfo,talkInfoVo);
            java.util.List<GraphVo> graphVos = graphInfoMapper.selectListByItemTypeAndId(ItemType.CONTENT, talkInfo.getId());
            talkInfoVo.setGraphVoList(graphVos);
            talkInfoVo.setSendTime(talkInfo.getCreateTime());
            UserInfo userInfo1=userInfoMapper.selectById(talkInfo.getSenduserid());
            talkInfoVo.setUserInfo(userInfo1);
            List1.add(talkInfoVo);
        }

        UserInfo userInfo=userInfoMapper.selectById(messageInfo.getUserid());
        MessageDetailVo messageDetailVo=new MessageDetailVo();
        BeanUtils.copyProperties(messageInfo, messageDetailVo);
        messageDetailVo.setUserInfo(userInfo);
        messageDetailVo.setGraphVoList(graphVoList);
        messageDetailVo.setSendTime(messageInfo.getCreateTime());
        messageDetailVo.setTalkList(List1);
        return messageDetailVo;
    }

    @Override
    public IPage<MessageOutVo> pageItemByUserId(Page<MessageOutVo> roomItemVoPage, Long id) {
        return mapper.pageItemByUserId(roomItemVoPage,id);
    }

    @Override
    public void saveOrUpdateMessage(MessageOutVo message) {
        message.setUserid(LoginUserHolder.getLoginUser().getUserId());
        super.saveOrUpdate(message);
        List<GraphVo> graphVoList = message.getGraphVoList();
        if(graphVoList!=null&&!graphVoList.isEmpty()) {
            ArrayList<GraphInfo> graphInfos = new ArrayList<>();
            for (GraphVo graphVo : graphVoList) {
                GraphInfo graphInfo = new GraphInfo();
                graphInfo.setItemType(ItemType.MESSAGE);
                graphInfo.setItemId(message.getId());
                graphInfo.setName(graphVo.getName());
                graphInfo.setUrl(graphVo.getUrl());
                graphInfos.add(graphInfo);
            }
            graphInfoService.saveBatch(graphInfos);
        }
    }

    @Override
    public IPage<MessageOutVo> pageItem2(Page<MessageOutVo> messageItemVoPage, String keyword) {
        return mapper.pageItem2(messageItemVoPage,keyword);
    }
}
