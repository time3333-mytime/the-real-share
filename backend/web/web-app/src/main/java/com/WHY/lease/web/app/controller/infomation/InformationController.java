package com.WHY.lease.web.app.controller.infomation;

import com.WHY.lease.common.login.LoginUserHolder;
import com.WHY.lease.common.result.Result;
import com.WHY.lease.model.entity.FriendsInfo;
import com.WHY.lease.web.app.service.FriendsInfoService;
import com.WHY.lease.web.app.service.InformationService;
import com.WHY.lease.web.app.vo.infomation.FindFriendsVo;
import com.WHY.lease.web.app.vo.infomation.FriendsInfoVo;
import com.WHY.lease.web.app.vo.infomation.InformationVo;
import com.WHY.lease.web.app.vo.user.NewUserInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/information")
@Tag(name = "消息管理")
public class InformationController {
    @Autowired
    private FriendsInfoService service;
    @Autowired
    private InformationService informationService;
    @Operation(summary = "根据用户id分页查询好友列表")
    @GetMapping("pageItemByUserId")
    public Result<IPage<FriendsInfoVo>> pageItemByApartmentId(@RequestParam long current, @RequestParam long size) {
        Page<FriendsInfoVo> friendsInfoVoPage = new Page<>(current,size);
        IPage<FriendsInfoVo>result=service.pageItemFriends(friendsInfoVoPage, LoginUserHolder.getLoginUser().getUserId());
        return Result.ok(result);
    }
    @GetMapping("talking")
    @Operation(summary = "获取用户信息")
    public Result<NewUserInfoVo> idInfo(@RequestParam Long userid) {
        NewUserInfoVo userInfoVo= service.getUserByid(userid);
        return Result.ok(userInfoVo);
    }
    @GetMapping("me")
    @Operation(summary = "获取用户信息")
    public Result<NewUserInfoVo> idInfo() {
        NewUserInfoVo userInfoVo= service.getme(LoginUserHolder.getLoginUser().getUserId());
        return Result.ok(userInfoVo);
    }

    @GetMapping("noRead")
    @Operation(summary = "获取未读消息")
    public Result<List<InformationVo>> noRead(@RequestParam Long userid) {
        LambdaQueryWrapper<InformationVo> lambdaQueryWrapper= new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(InformationVo::getToId,LoginUserHolder.getLoginUser().getUserId())
                .eq(InformationVo::getFromId,userid)
                .eq(InformationVo::getStatus,1);
        List<InformationVo> list = informationService.list(lambdaQueryWrapper);
        return Result.ok(list);
    }
    @GetMapping("meRead")
    @Operation(summary = "获取all消息")
    public Result<List<InformationVo>> meRead(@RequestParam Long userid) {
        List<InformationVo> list =  informationService.meRead(LoginUserHolder.getLoginUser().getUserId(),userid);
        LambdaUpdateWrapper<InformationVo> lambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(InformationVo::getToId,LoginUserHolder.getLoginUser().getUserId());
        lambdaUpdateWrapper.eq(InformationVo::getFromId,userid);
        lambdaUpdateWrapper.set(InformationVo::getStatus,1);
        informationService.update(lambdaUpdateWrapper);
        return Result.ok(list);
    }

    @Operation(summary = "加好友申请")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody InformationVo informationVo) {
        service.saveOrUpdateFriend(informationVo,0);
        return Result.ok();
    }
    @Operation(summary = "房东特权")
    @PostMapping("saveOrUpdate2")
    public Result saveOrUpdate2(@RequestBody InformationVo informationVo) {
        service.saveOrUpdateFriend(informationVo,1);
        return Result.ok();
    }
    @Operation(summary = "租客特权")
    @PostMapping("saveOrUpdate3")
    public Result saveOrUpdate3(@RequestBody InformationVo informationVo) {
        service.saveOrUpdateFriend2(informationVo,1);
        return Result.ok();
    }
    @Operation(summary = "根据用户id检索未读消息个数")
    @PostMapping("getNotRead")
    public Result<Integer> getNotRead() {
        LambdaQueryWrapper<FriendsInfo> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(FriendsInfo::getFirstId,LoginUserHolder.getLoginUser().getUserId());
        lambdaQueryWrapper.eq(FriendsInfo::getStatus,0);
        List<FriendsInfo> list = service.list(lambdaQueryWrapper);
        return Result.ok(list.size());
    }

    @Operation(summary = "查看好友申请")
    @GetMapping("getFriend")
    public Result<IPage<FindFriendsVo>> getFriend(@RequestParam long current, @RequestParam long size) {
        Page<FindFriendsVo> friendsInfoVoPage = new Page<>(current,size);
        IPage<FindFriendsVo>result=service.pageItemgetFriends(friendsInfoVoPage, LoginUserHolder.getLoginUser().getUserId());
        System.out.println(LoginUserHolder.getLoginUser().getUserId());
        return Result.ok(result);
    }

    @Operation(summary = "通过好友申请")
    @PostMapping("pass")
    public Result pass(@RequestBody FriendsInfo friendsInfo) {
        System.err.println(friendsInfo.getFirstId());
        service.pass(friendsInfo.getFirstId(),LoginUserHolder.getLoginUser().getUserId());
        return Result.ok();
    }


    @Operation(summary = "拒绝好友申请")
    @PostMapping("reject")
    public Result reject(@RequestBody FriendsInfo friendsInfo) {
        service.reject(friendsInfo.getFirstId(),LoginUserHolder.getLoginUser().getUserId());
        return Result.ok();
    }
    @Operation(summary = "查看好友申请")
    @GetMapping("isFriend")
    public Result<Boolean> isFriend(@RequestParam Long id) {
        if(id.equals(LoginUserHolder.getLoginUser().getUserId()))return Result.ok(false);
        List<Long> mylist=service.getFriendsId(LoginUserHolder.getLoginUser().getUserId());
        if(mylist!=null){
            for (int i = 0; i < mylist.size(); i++) {
                if(mylist.get(i).equals(id)){return Result.ok(false);}
            }
        }
        return Result.ok(true);
    }
}
