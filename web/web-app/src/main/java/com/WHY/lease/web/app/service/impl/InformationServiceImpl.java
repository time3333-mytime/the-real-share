package com.WHY.lease.web.app.service.impl;

import com.WHY.lease.web.app.service.InformationService;
import com.WHY.lease.web.app.mapper.InformationMapper;
import com.WHY.lease.web.app.vo.infomation.InformationVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InformationServiceImpl extends ServiceImpl<InformationMapper, InformationVo>
        implements InformationService {

    @Autowired
    private InformationMapper mapper;
    @Override
    public List<InformationVo> meRead(Long userId, Long userid) {
        return mapper.meRead(userId,userid);//diyige benren
    }
}
