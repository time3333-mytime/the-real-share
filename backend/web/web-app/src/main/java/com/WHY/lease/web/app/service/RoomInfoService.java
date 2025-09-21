package com.WHY.lease.web.app.service;

import com.WHY.lease.model.entity.RoomInfo;
import com.WHY.lease.web.app.vo.message.TalkInfoVo;
import com.WHY.lease.web.app.vo.room.RoomDetailVo;
import com.WHY.lease.web.app.vo.room.RoomItemVo;
import com.WHY.lease.web.app.vo.room.RoomQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author liubo
* @description 针对表【room_info(房间信息表)】的数据库操作Service
* @createDate 2023-07-26 11:12:39
*/
public interface RoomInfoService extends IService<RoomInfo> {
    IPage<RoomItemVo> pageItem(Page<RoomItemVo> roomItemVoPage, RoomQueryVo queryVo);

    RoomDetailVo getDetailById(Long id);

    IPage<RoomItemVo> pageItemByApartmentId(Page<RoomItemVo> roomItemVoPage, Long id);

    List<TalkInfoVo> getTalkDetailByid(Long id);

    IPage<RoomItemVo> pageItem2(Page<RoomItemVo> roomItemVoPage, Long userId);

    Boolean getIsOut(Long id);
}
