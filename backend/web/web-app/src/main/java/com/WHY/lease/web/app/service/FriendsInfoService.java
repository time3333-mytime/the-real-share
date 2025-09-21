package com.WHY.lease.web.app.service;

import com.WHY.lease.web.app.vo.infomation.FindFriendsVo;
import com.WHY.lease.web.app.vo.infomation.FriendsInfoVo;
import com.WHY.lease.model.entity.FriendsInfo;
import com.WHY.lease.web.app.vo.infomation.InformationVo;
import com.WHY.lease.web.app.vo.user.NewUserInfoVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface FriendsInfoService extends IService<FriendsInfo> {
    IPage<FriendsInfoVo> pageItemFriends(Page<FriendsInfoVo> friendsInfoVoPage, Long userId);

    NewUserInfoVo getUserByid(Long userid);

    NewUserInfoVo getme(Long userId);

    void saveOrUpdateFriend(InformationVo informationVo,Integer status);

    IPage<FindFriendsVo> pageItemgetFriends(Page<FindFriendsVo> friendsInfoVoPage, Long userId);

    void pass(Long Id ,Long id);

    void reject(Long firstId, Long userId);

    List<Long> getFriendsId(Long id);

    void saveOrUpdateFriend2(InformationVo informationVo, int status);
}
