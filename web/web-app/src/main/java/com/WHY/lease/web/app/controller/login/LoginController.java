package com.WHY.lease.web.app.controller.login;


import com.WHY.lease.common.login.LoginUserHolder;
import com.WHY.lease.common.result.Result;
import com.WHY.lease.web.app.service.LoginService;
import com.WHY.lease.web.app.service.UserInfoService;
import com.WHY.lease.web.app.vo.user.LoginVo;
import com.WHY.lease.web.app.vo.user.UserInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "登录管理")
@RestController
@RequestMapping("/app/")
public class LoginController {
        @Autowired
    private LoginService service;
        @Autowired
        private UserInfoService userInfoService;

    @GetMapping("login/getCode")
    @Operation(summary = "获取短信验证码")
    public Result getCode(@RequestParam String phone) {
        service.getCode(phone);
        return Result.ok();
    }

    @PostMapping("login")
    @Operation(summary = "登录")
    public Result<String> login(@RequestBody LoginVo loginVo) {
        String token=service.loginin(loginVo);
        return Result.ok(token);
    }
    @PostMapping("load/login")
    @Operation(summary = "登录")
    public Result<String> loadLogin(@RequestBody LoginVo loginVo) {
        String token=service.loadLoginin(loginVo);
        return Result.ok(token);
    }

    @GetMapping("info")
    @Operation(summary = "获取登录用户信息")
    public Result<UserInfoVo> info() {
        Long userId = LoginUserHolder.getLoginUser().getUserId();
        UserInfoVo userInfoVo= service.getLoginUserByid(userId);
        return Result.ok(userInfoVo);
    }

    @PostMapping("change")
    @Operation(summary = "换头像")
    public Result change(@RequestBody UserInfoVo userInfoVo) {
        service.change(userInfoVo.getAvatarUrl(),LoginUserHolder.getLoginUser().getUserId());
        return Result.ok();
    }

    @PostMapping("changeName")
    @Operation(summary = "换名字")
    public Result changeName(@RequestBody UserInfoVo userInfoVo) {
        service.changeName(userInfoVo.getNickname(), LoginUserHolder.getLoginUser().getUserId());
        return Result.ok();
    }
}

