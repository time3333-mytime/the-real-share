package com.WHY.lease.web.app.service;

import com.WHY.lease.model.entity.MessageInfo;
import com.WHY.lease.web.app.vo.message.MessageDetailVo;
import com.WHY.lease.web.app.vo.message.MessageOutVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;


public interface MessageInfoService extends IService<MessageInfo> {
    IPage<MessageOutVo> pageItem(Page<MessageOutVo> messageItemVoPage);

    MessageDetailVo getDetailById(Long id);

    IPage<MessageOutVo> pageItemByUserId(Page<MessageOutVo> roomItemVoPage, Long id);

    void saveOrUpdateMessage(MessageOutVo message);

    IPage<MessageOutVo> pageItem2(Page<MessageOutVo> messageItemVoPage, String keyword);
}
