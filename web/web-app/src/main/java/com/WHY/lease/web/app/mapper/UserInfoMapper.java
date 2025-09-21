package com.WHY.lease.web.app.mapper;

import com.WHY.lease.model.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author liubo
* @description 针对表【user_info(用户信息表)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.atguigu.lease.model.entity.UserInfo
*/
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    void change(String avatarUrl, Long userId);

    void changeName(String name, Long userId);
}




