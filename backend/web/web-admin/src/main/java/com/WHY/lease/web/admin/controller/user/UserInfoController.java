package com.WHY.lease.web.admin.controller.user;


import com.WHY.lease.common.result.Result;
import com.WHY.lease.model.entity.UserInfo;
import com.WHY.lease.model.enums.BaseStatus;
import com.WHY.lease.web.admin.vo.user.UserInfoQueryVo;
import com.WHY.lease.web.admin.service.UserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户信息管理")
@RestController
@RequestMapping("/admin/user")
public class UserInfoController {

    @Autowired
    private UserInfoService service;

    @Operation(summary = "分页查询用户信息")
    @GetMapping("page")
    public Result<IPage<UserInfo>> pageUserInfo(@RequestParam long current, @RequestParam long size, UserInfoQueryVo queryVo) {
        Page<UserInfo> page = new Page<>(current,size);
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(queryVo.getPhone()!=null,UserInfo::getPhone,queryVo.getPhone());
        lambdaQueryWrapper.eq(queryVo.getStatus()!=null,UserInfo::getStatus,queryVo.getStatus());
        lambdaQueryWrapper.ne(UserInfo::getStatus,BaseStatus.LANDLORD_ENABLE);
        lambdaQueryWrapper.ne(UserInfo::getStatus,BaseStatus.LANDLORD_DISABLE);
        Page<UserInfo> page1 = service.page(page, lambdaQueryWrapper);
        return Result.ok(page1);
    }
    @Operation(summary = "分页查询用户信息")
    @GetMapping("page2")
    public Result<IPage<UserInfo>> pageUserInfo2(@RequestParam long current, @RequestParam long size, UserInfoQueryVo queryVo) {
        Page<UserInfo> page = new Page<>(current,size);
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(queryVo.getPhone()!=null,UserInfo::getPhone,queryVo.getPhone());
        lambdaQueryWrapper.eq(queryVo.getStatus()!=null,UserInfo::getStatus,queryVo.getStatus());
        lambdaQueryWrapper.ne(UserInfo::getStatus,BaseStatus.DISABLE);
        lambdaQueryWrapper.ne(UserInfo::getStatus,BaseStatus.ENABLE);
        Page<UserInfo> page1 = service.page(page, lambdaQueryWrapper);
        return Result.ok(page1);
    }
    @Operation(summary = "根据用户id更新账号状态")
    @PostMapping("updateStatusById")
    public Result updateStatusById(@RequestParam Long id, @RequestParam BaseStatus status) {
        LambdaUpdateWrapper<UserInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserInfo::getId, id);
        updateWrapper.set(UserInfo::getStatus, status);
        service.update(updateWrapper);
        return Result.ok();
    }
}
