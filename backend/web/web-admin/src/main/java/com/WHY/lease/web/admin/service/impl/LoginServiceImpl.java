package com.WHY.lease.web.admin.service.impl;

import com.WHY.lease.common.exception.LeaseException;
import com.WHY.lease.common.result.ResultCodeEnum;
import com.WHY.lease.common.utils.JwtUtil;
import com.WHY.lease.model.entity.SystemUser;
import com.WHY.lease.model.enums.BaseStatus;
import com.WHY.lease.web.admin.vo.login.CaptchaVo;
import com.WHY.lease.web.admin.vo.login.LoginVo;
import com.WHY.lease.web.admin.vo.system.user.SystemUserInfoVo;
import com.WHY.lease.web.admin.mapper.SystemUserMapper;
import com.WHY.lease.web.admin.service.LoginService;
import com.wf.captcha.SpecCaptcha;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    public SystemUserMapper systemUserMapper;

    @Override
    public CaptchaVo getCaptcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);

        String code=specCaptcha.text().toLowerCase();
        String key="admin:login:"+UUID.randomUUID();
        stringRedisTemplate.opsForValue().set(key,code,60, TimeUnit.SECONDS);

        return new CaptchaVo(specCaptcha.toBase64(),key);
    }

    @Override
    public String loginin(LoginVo loginVo) {

        if(loginVo.getCaptchaCode()==null){
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_NOT_FOUND);
        }
        String code = stringRedisTemplate.opsForValue().get(loginVo.getCaptchaKey());
        if(code==null) throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_EXPIRED);

        if(!code.equals(loginVo.getCaptchaCode().toLowerCase()))throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_ERROR);

        SystemUser systemUser = systemUserMapper.selectOneByUsername(loginVo.getUsername());
        if(systemUser==null) throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_NOT_EXIST_ERROR);

        if(systemUser.getStatus()== BaseStatus.DISABLE) throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_DISABLED_ERROR);

        if(!systemUser.getPassword().equals(DigestUtils.md5Hex(loginVo.getPassword()))) throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_ERROR);


        return JwtUtil.createToken(systemUser.getId(),systemUser.getUsername());
    }

    @Override
    public SystemUserInfoVo getLoginUserInfoById(Long userId) {
        SystemUser systemUser=systemUserMapper.selectById(userId);
        SystemUserInfoVo systemUserInfoVo = new SystemUserInfoVo();
        systemUserInfoVo.setName(systemUser.getName());
        systemUserInfoVo.setAvatarUrl(systemUser.getAvatarUrl());
        return systemUserInfoVo;
    }
}
