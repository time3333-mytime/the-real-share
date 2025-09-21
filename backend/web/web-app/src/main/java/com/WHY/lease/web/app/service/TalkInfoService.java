package com.WHY.lease.web.app.service;

import com.WHY.lease.web.app.vo.message.ContentInfoVo;
import com.WHY.lease.web.app.vo.message.TalkInfoVo;
import com.WHY.lease.model.entity.talkInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface TalkInfoService extends IService<talkInfo> {
    void saveContent(TalkInfoVo talkInfoVo);

    IPage<ContentInfoVo> getRead(Page<ContentInfoVo> talkInfoVoPage, Long id);

    void saveContent2(TalkInfoVo talkInfoVo);

    IPage<ContentInfoVo> getRead2(Page<ContentInfoVo> contentInfoVoPage, Long userId);
}
