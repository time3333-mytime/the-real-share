package com.WHY.lease.web.app.mapper;

import com.WHY.lease.web.app.vo.infomation.FindFriendsVo;
import com.WHY.lease.web.app.vo.infomation.FriendsInfoVo;
import com.WHY.lease.model.entity.FriendsInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface FriendsInfoMapper extends BaseMapper<FriendsInfo> {
    IPage<FriendsInfoVo> pageItemFriends(Page<FriendsInfoVo> friendsInfoVoPage, Long id);

    IPage<FindFriendsVo> pageItemgetFriends(Page<FindFriendsVo> friendsInfoVoPage, Long userId);

    void pass(Long Id ,Long id);

    void reject(Long firstId, Long userId);

    List<Long> getFriendsId(Long id);

}
