package com.WHY.lease.web.app.mapper;

import com.WHY.lease.model.entity.MessageInfo;
import com.WHY.lease.web.app.vo.message.MessageOutVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


public interface MessageInfoMapper extends BaseMapper<MessageInfo> {
    IPage<MessageOutVo> pageItem(Page<MessageOutVo> messageItemVoPage);

    IPage<MessageOutVo> pageItemByUserId(Page<MessageOutVo> roomItemVoPage, Long id);

    IPage<MessageOutVo> pageItem2(Page<MessageOutVo> messageItemVoPage, String keyword);
}
