package com.WHY.lease.web.app.service;

import com.WHY.lease.web.app.vo.infomation.InformationVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface InformationService extends IService<InformationVo> {
    List<InformationVo> meRead(Long userId, Long userid);
}
