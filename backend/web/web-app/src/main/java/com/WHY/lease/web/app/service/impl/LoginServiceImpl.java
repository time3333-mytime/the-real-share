package com.WHY.lease.web.app.service.impl;

import com.WHY.lease.common.constant.RedisConstant;
import com.WHY.lease.common.exception.LeaseException;
import com.WHY.lease.common.result.ResultCodeEnum;
import com.WHY.lease.common.utils.CodeUtils;
import com.WHY.lease.common.utils.JwtUtil;
import com.WHY.lease.model.entity.UserInfo;
import com.WHY.lease.model.enums.BaseStatus;
import com.WHY.lease.web.app.vo.user.LoginVo;
import com.WHY.lease.web.app.vo.user.UserInfoVo;
import com.WHY.lease.web.app.mapper.UserInfoMapper;
import com.WHY.lease.web.app.service.LoginService;
import com.WHY.lease.web.app.service.SmsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private SmsService smsService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    JavaMailSender sender;

    @Override
    public void getCode(String phone) {
        String code= CodeUtils.getRandomCode(6);
        String key= RedisConstant.APP_LOGIN_PREFIX+phone;

        Boolean b = redisTemplate.hasKey(key);

        if(b){
            Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
            if(RedisConstant.APP_LOGIN_CODE_TTL_SEC - expire<RedisConstant.APP_LOGIN_CODE_RESEND_TIME_SEC){
                throw new LeaseException(ResultCodeEnum.APP_SEND_SMS_TOO_OFTEN);
            }
        }
//        smsService.sendCode(phone,code);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("关于您的验证码");
        message.setText("尊敬的用户您好，您的验证码是"+code+"。将在十分钟后过期");
        message.setTo(phone);
        message.setFrom();//填你自己的
        sender.send(message);

        redisTemplate.opsForValue().set(key,code,RedisConstant.APP_LOGIN_CODE_TTL_SEC, TimeUnit.SECONDS);
    }

    @Override
    public String loginin(LoginVo loginVo) {
        if(loginVo.getPhone()==null)throw new LeaseException(ResultCodeEnum.APP_LOGIN_PHONE_EMPTY);

        if(loginVo.getCode()==null)throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_EMPTY);

        String key= RedisConstant.APP_LOGIN_PREFIX+loginVo.getPhone();

        String code = redisTemplate.opsForValue().get(key);
        if(code==null)throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_EXPIRED);

        if(!code.equals(loginVo.getCode()))throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_ERROR);

        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getPhone, loginVo.getPhone());
        queryWrapper.eq(UserInfo::getStatus,BaseStatus.ENABLE);
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);

        if(userInfo==null){
            userInfo=new UserInfo();
            userInfo.setPhone(loginVo.getPhone());
            userInfo.setStatus(BaseStatus.ENABLE);
            userInfo.setNickname("用户-"+loginVo.getPhone().substring(0,6));
            userInfoMapper.insert(userInfo);
        }
        else{
            if(userInfo.getStatus()==BaseStatus.DISABLE) throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_DISABLED_ERROR);
        }

        return JwtUtil.createToken(userInfo.getId(),loginVo.getPhone());
    }

    @Override
    public UserInfoVo getLoginUserByid(Long userId) {
        UserInfo userInfo = userInfoMapper.selectById(userId);
        UserInfoVo userInfoVo = new UserInfoVo(userInfo.getNickname(), userInfo.getAvatarUrl());
        return userInfoVo;
    }

    @Override
    public UserInfoVo getUserByid(Long userid) {
        UserInfo userInfo = userInfoMapper.selectById(userid);
        UserInfoVo userInfoVo = new UserInfoVo(userInfo.getNickname(), userInfo.getAvatarUrl());
        return userInfoVo;
    }

    @Override
    public void change(String avatarUrl, Long userId) {
        userInfoMapper.change(avatarUrl,userId);
    }

    @Override
    public void changeName(String name, Long userId) {
        userInfoMapper.changeName(name,userId);
    }

    @Override
    public String loadLoginin(LoginVo loginVo) {
        if(loginVo.getPhone()==null)throw new LeaseException(ResultCodeEnum.APP_LOGIN_PHONE_EMPTY);

        if(loginVo.getCode()==null)throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_EMPTY);

        String key= RedisConstant.APP_LOGIN_PREFIX+loginVo.getPhone();

        String code = redisTemplate.opsForValue().get(key);
        if(code==null)throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_EXPIRED);

        if(!code.equals(loginVo.getCode()))throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_ERROR);

        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getPhone, loginVo.getPhone());
        queryWrapper.eq(UserInfo::getStatus,BaseStatus.LANDLORD_ENABLE);
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);

        if(userInfo==null){
            userInfo=new UserInfo();
            userInfo.setPhone(loginVo.getPhone());
            userInfo.setStatus(BaseStatus.LANDLORD_ENABLE);
            userInfo.setNickname("用户-"+loginVo.getPhone().substring(0,6));
            userInfoMapper.insert(userInfo);
        }
        else{
            if(userInfo.getStatus()==BaseStatus.LANDLORD_DISABLE) throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_DISABLED_ERROR);
        }

        return JwtUtil.createToken(userInfo.getId(),loginVo.getPhone());
    }
}
