package com.WHY.lease.web.app.mapper;

import com.WHY.lease.web.app.vo.message.ContentInfoVo;
import com.WHY.lease.model.entity.talkInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface TalkInfoMapper extends BaseMapper<talkInfo> {
    IPage<ContentInfoVo> getRead(Page<ContentInfoVo> talkInfoVoPage, Long id);

    IPage<ContentInfoVo> getRead2(Page<ContentInfoVo> contentInfoVoPage, Long userId);
}
