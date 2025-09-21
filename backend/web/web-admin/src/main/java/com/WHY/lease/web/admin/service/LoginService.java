package com.WHY.lease.web.admin.service;

import com.WHY.lease.web.admin.vo.login.CaptchaVo;
import com.WHY.lease.web.admin.vo.login.LoginVo;
import com.WHY.lease.web.admin.vo.system.user.SystemUserInfoVo;

public interface LoginService {

    CaptchaVo getCaptcha();

    String loginin(LoginVo loginVo);

    SystemUserInfoVo getLoginUserInfoById(Long userId);
}
