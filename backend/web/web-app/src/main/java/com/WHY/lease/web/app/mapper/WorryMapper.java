package com.WHY.lease.web.app.mapper;

import com.WHY.lease.model.entity.WorryInfo;
import com.WHY.lease.web.app.vo.message.WorryInfoVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface WorryMapper  extends BaseMapper<WorryInfo> {
    IPage<WorryInfoVo> pageItem(Page<WorryInfoVo> page, Long userId);
}
