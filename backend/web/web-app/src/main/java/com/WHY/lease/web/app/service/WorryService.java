package com.WHY.lease.web.app.service;

import com.WHY.lease.model.entity.WorryInfo;
import com.WHY.lease.web.app.vo.message.MessageOutVo;
import com.WHY.lease.web.app.vo.message.WorryInfoVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface WorryService extends IService<WorryInfo> {
    void saveOrUpdateWorry(MessageOutVo message);

    IPage<WorryInfoVo> pageItem(Page<WorryInfoVo> page, Long userId);
}
