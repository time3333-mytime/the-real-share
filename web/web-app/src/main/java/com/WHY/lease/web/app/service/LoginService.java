package com.WHY.lease.web.app.service;

import com.WHY.lease.web.app.vo.user.LoginVo;
import com.WHY.lease.web.app.vo.user.UserInfoVo;

public interface LoginService {
    void getCode(String phone);

    String loginin(LoginVo loginVo);

    UserInfoVo getLoginUserByid(Long userId);

    UserInfoVo getUserByid(Long userid);

    void change(String avatarUrl, Long userId);

    void changeName(String name, Long userId);

    String loadLoginin(LoginVo loginVo);
}
