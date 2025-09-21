package com.WHY.lease.web.admin.service.impl;

import com.WHY.lease.model.entity.UserInfo;
import com.WHY.lease.web.admin.mapper.UserInfoMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.WHY.lease.web.admin.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
* @author liubo
* @description 针对表【user_info(用户信息表)】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{

}




