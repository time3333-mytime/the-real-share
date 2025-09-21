package com.WHY.lease.web.app.service.impl;

import com.WHY.lease.common.login.LoginUserHolder;
import com.WHY.lease.model.entity.RoomInfo;
import com.WHY.lease.model.entity.UserInfo;
import com.WHY.lease.web.app.mapper.FriendsInfoMapper;
import com.WHY.lease.web.app.mapper.RoomInfoMapper;
import com.WHY.lease.web.app.service.FriendsInfoService;
import com.WHY.lease.web.app.service.RoomInfoService;
import com.WHY.lease.web.app.vo.infomation.FindFriendsVo;
import com.WHY.lease.web.app.vo.infomation.FriendsInfoVo;
import com.WHY.lease.model.entity.FriendsInfo;
import com.WHY.lease.web.app.mapper.UserInfoMapper;
import com.WHY.lease.web.app.vo.infomation.InformationVo;
import com.WHY.lease.web.app.vo.user.NewUserInfoVo;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendsInfoServiceImpl extends ServiceImpl<FriendsInfoMapper, FriendsInfo> implements FriendsInfoService {

    @Autowired
    private FriendsInfoMapper mapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private RoomInfoMapper roomInfoMapper;
    @Override
    public IPage<FriendsInfoVo> pageItemFriends(Page<FriendsInfoVo> friendsInfoVoPage, Long userId) {
        return mapper.pageItemFriends(friendsInfoVoPage,userId);
    }

    @Override
    public NewUserInfoVo getUserByid(Long userid) {
        UserInfo userInfo = userInfoMapper.selectById(userid);
        NewUserInfoVo userInfoVo = new NewUserInfoVo(userInfo.getId(),userInfo.getNickname(), userInfo.getAvatarUrl());
        return userInfoVo;
    }

    @Override
    public NewUserInfoVo getme(Long userId) {
        UserInfo userInfo = userInfoMapper.selectById(userId);
        NewUserInfoVo newUserInfoVo=new NewUserInfoVo(userInfo.getId(),userInfo.getNickname(),userInfo.getAvatarUrl());
        return newUserInfoVo;
    }

    @Override
    public void saveOrUpdateFriend(InformationVo informationVo,Integer status) {
        FriendsInfo friendsInfo=new FriendsInfo(informationVo.getToId(),LoginUserHolder.getLoginUser().getUserId(),status);
        mapper.insert(friendsInfo);
    }

    @Override
    public IPage<FindFriendsVo> pageItemgetFriends(Page<FindFriendsVo> friendsInfoVoPage, Long userId) {
        return mapper.pageItemgetFriends(friendsInfoVoPage,userId);
    }

    @Override
    public void pass(Long Id ,Long id) {
        mapper.pass(Id, id);
    }

    @Override
    public void reject(Long firstId, Long userId) {
        mapper.reject(firstId,userId);
    }

    @Override
    public List<Long> getFriendsId(Long id) {
        return mapper.getFriendsId(id);
    }

    @Override
    public void saveOrUpdateFriend2(InformationVo informationVo, int status) {
        RoomInfo roomInfo = roomInfoMapper.selectById(informationVo.getToId());
        FriendsInfo friendsInfo=new FriendsInfo(roomInfo.getLandlordId(),LoginUserHolder.getLoginUser().getUserId(),status);
        mapper.insert(friendsInfo);
    }
}
