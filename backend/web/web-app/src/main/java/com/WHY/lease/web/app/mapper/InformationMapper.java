package com.WHY.lease.web.app.mapper;

import com.WHY.lease.web.app.vo.infomation.InformationVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface InformationMapper extends BaseMapper<InformationVo> {
    List<InformationVo> meRead(Long userId, Long userid);
}
