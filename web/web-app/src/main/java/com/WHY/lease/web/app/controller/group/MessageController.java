package com.WHY.lease.web.app.controller.group;

import com.WHY.lease.common.login.LoginUserHolder;
import com.WHY.lease.common.result.Result;
import com.WHY.lease.model.entity.WorryInfo;
import com.WHY.lease.web.app.service.MessageInfoService;
import com.WHY.lease.web.app.service.WorryService;
import com.WHY.lease.web.app.vo.message.*;
import com.WHY.lease.model.entity.talkInfo;
import com.WHY.lease.web.app.service.TalkInfoService;
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
@RequestMapping("/app/message")
@Tag(name = "帖子管理")
public class MessageController {

    @Autowired
    private MessageInfoService service;
    @Autowired
    private WorryService worryService;

    @Autowired
    private TalkInfoService talkInfoService;



    @Operation(summary = "保存或更新帖子信息")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody MessageOutVo message) {
        service.saveOrUpdateMessage(message);
        return Result.ok();
    }
    @Operation(summary = "保存或更新报修信息")
    @PostMapping("worry")
    public Result saveOrUpdateWorry(@RequestBody MessageOutVo message) {
        worryService.saveOrUpdateWorry(message);
        return Result.ok();
    }
    @Operation(summary = "根据用户id检索未读消息个数")
    @PostMapping("getNotRead")
    public Result<Integer> getNotRead() {
        LambdaQueryWrapper<talkInfo> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(talkInfo::getUserid,LoginUserHolder.getLoginUser().getUserId());
        lambdaQueryWrapper.eq(talkInfo::getStatus,1);
        lambdaQueryWrapper.ne(talkInfo::getSenduserid,LoginUserHolder.getLoginUser().getUserId());
        List<talkInfo> list = talkInfoService.list(lambdaQueryWrapper);
        return Result.ok(list.size());
    }
    @Operation(summary = "根据房东id检索未读消息个数")
    @PostMapping("getNotRead2")
    public Result<Integer> getNotRead2() {
        LambdaQueryWrapper<talkInfo> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(talkInfo::getUserid,LoginUserHolder.getLoginUser().getUserId());
        lambdaQueryWrapper.eq(talkInfo::getStatus,3);
        lambdaQueryWrapper.ne(talkInfo::getSenduserid,LoginUserHolder.getLoginUser().getUserId());
        List<talkInfo> list = talkInfoService.list(lambdaQueryWrapper);
        return Result.ok(list.size());
    }

    @Operation(summary = "根据用户id分页查询未读评论")
    @GetMapping("getRead")
    public Result<IPage<ContentInfoVo>> getRead(@RequestParam long current, @RequestParam long size) {
        Page<ContentInfoVo> contentInfoVoPage = new Page<>(current,size);
        IPage<ContentInfoVo> result=talkInfoService.getRead(contentInfoVoPage, LoginUserHolder.getLoginUser().getUserId());
        haveRead();
        return Result.ok(result);
    }
    @Operation(summary = "根据用户id分页查询未读评论")
    @GetMapping("getRead2")
    public Result<IPage<ContentInfoVo>> getRead2(@RequestParam long current, @RequestParam long size) {
        Page<ContentInfoVo> contentInfoVoPage = new Page<>(current,size);
        IPage<ContentInfoVo> result=talkInfoService.getRead2(contentInfoVoPage, LoginUserHolder.getLoginUser().getUserId());
        haveRead2();
        return Result.ok(result);
    }
    @Operation(summary = "查询后未读变成已读")
    @PostMapping("haveRead")
    public Result haveRead() {
        LambdaUpdateWrapper<talkInfo> lambdaQueryWrapper=new LambdaUpdateWrapper<>();
        lambdaQueryWrapper.eq(talkInfo::getStatus,1);
        lambdaQueryWrapper.eq(talkInfo::getUserid,LoginUserHolder.getLoginUser().getUserId());
        lambdaQueryWrapper.set(talkInfo::getStatus,0);
        talkInfoService.update(lambdaQueryWrapper);
        return Result.ok();
    }
    @Operation(summary = "查询后未读变成已读")
    @PostMapping("haveRead2")
    public Result haveRead2() {
        LambdaUpdateWrapper<talkInfo> lambdaQueryWrapper=new LambdaUpdateWrapper<>();
        lambdaQueryWrapper.eq(talkInfo::getStatus,3);
        lambdaQueryWrapper.eq(talkInfo::getUserid,LoginUserHolder.getLoginUser().getUserId());
        lambdaQueryWrapper.set(talkInfo::getStatus,4);
        talkInfoService.update(lambdaQueryWrapper);
        return Result.ok();
    }
    @Operation(summary = "保存或更新评论信息")
    @PostMapping("saveContent")
    public Result saveOrUpdate(@RequestBody TalkInfoVo talkInfoVo) {
        talkInfoService.saveContent(talkInfoVo);
        return Result.ok();
    }
    @Operation(summary = "保存或更新评论信息")
    @PostMapping("saveContent2")
    public Result saveOrUpdate2(@RequestBody TalkInfoVo talkInfoVo) {
        talkInfoService.saveContent2(talkInfoVo);
        return Result.ok();
    }

    @Operation(summary = "分页查询帖子列表")
    @GetMapping("pageItem")
    public Result<IPage<MessageOutVo>> pageItem(@RequestParam long current, @RequestParam long size) {
        Page<MessageOutVo> messageItemVoPage = new Page<>(current,size);
        IPage<MessageOutVo> result=service.pageItem(messageItemVoPage);
        return Result.ok(result);
    }
    @Operation(summary = "分页查询帖子列表")
    @GetMapping("pageItem2")
    public Result<IPage<MessageOutVo>> pageItem2(@RequestParam long current, @RequestParam long size,@RequestParam String keyword) {
        Page<MessageOutVo> messageItemVoPage = new Page<>(current,size);
        IPage<MessageOutVo> result=service.pageItem2(messageItemVoPage,keyword);
        return Result.ok(result);
    }
    @Operation(summary = "getWorry")
    @GetMapping("get/worry")
    public Result<IPage<WorryInfoVo>> getWorry(@RequestParam long current, @RequestParam long size) {
        Page<WorryInfoVo> page=new Page<>(current,size);
        IPage<WorryInfoVo> worryInfoIpage= worryService.pageItem(page,LoginUserHolder.getLoginUser().getUserId());
        return Result.ok(worryInfoIpage);
    }
    @Operation(summary = "根据id获取帖子的详细信息")
    @GetMapping("getDetailById")
    public Result<MessageDetailVo> getDetailById(@RequestParam Long id) {
        MessageDetailVo messageDetailVo = service.getDetailById(id);
        return Result.ok(messageDetailVo);
    }

    @Operation(summary = "根据用户id分页查询帖子列表")
    @GetMapping("pageItemByUserId")
    public Result<IPage<MessageOutVo>> pageItemByApartmentId(@RequestParam long current, @RequestParam long size, @RequestParam Long id) {
        Page<MessageOutVo> roomItemVoPage = new Page<>(current,size);
        IPage<MessageOutVo>result=service.pageItemByUserId(roomItemVoPage,id);
        return Result.ok(result);
    }

}
