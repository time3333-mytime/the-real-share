package com.WHY.lease.web.app.controller.room;


import com.WHY.lease.common.login.LoginUserHolder;
import com.WHY.lease.common.result.Result;
import com.WHY.lease.model.entity.UserInfo;
import com.WHY.lease.model.entity.talkInfo;
import com.WHY.lease.model.enums.ItemType;
import com.WHY.lease.web.app.service.RoomInfoService;
import com.WHY.lease.web.app.service.TalkInfoService;
import com.WHY.lease.web.app.vo.graph.GraphVo;
import com.WHY.lease.web.app.vo.message.TalkInfoVo;
import com.WHY.lease.web.app.vo.room.RoomDetailVo;
import com.WHY.lease.web.app.vo.room.RoomItemVo;
import com.WHY.lease.web.app.vo.room.RoomQueryVo;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "房间信息")
@RestController
@RequestMapping("/app/room")
public class RoomController {
    @Autowired
    private RoomInfoService service;
    @Autowired
    private TalkInfoService talkInfoService;

    @Operation(summary = "分页查询房间列表")
    @GetMapping("pageItem")
    public Result<IPage<RoomItemVo>> pageItem(@RequestParam long current, @RequestParam long size, RoomQueryVo queryVo) {
        Page<RoomItemVo> roomItemVoPage = new Page<>(current,size);
        IPage<RoomItemVo> result=service.pageItem(roomItemVoPage,queryVo);
        return Result.ok(result);
    }
    @Operation(summary = "分页查询房间列表")
    @GetMapping("pageItem2")
    public Result<IPage<RoomItemVo>> pageItem2(@RequestParam long current, @RequestParam long size) {
        Page<RoomItemVo> roomItemVoPage = new Page<>(current,size);
        IPage<RoomItemVo> result=service.pageItem2(roomItemVoPage, LoginUserHolder.getLoginUser().getUserId());
        return Result.ok(result);
    }

    @Operation(summary = "根据id获取房间的详细信息")
    @GetMapping("getDetailById")
    public Result<RoomDetailVo> getDetailById(@RequestParam Long id) {
        RoomDetailVo roomInfo = service.getDetailById(id);
        return Result.ok(roomInfo);
    }
    @Operation(summary = "根据id")
    @GetMapping("isOut")
    public Result<Boolean> getIsOut(@RequestParam Long id) {
        Boolean isOut = service.getIsOut(id);
        return Result.ok(isOut);
    }
    @Operation(summary = "根据id获取房间的详细信息")
    @GetMapping("getTalkDetailById")
    public Result<List<TalkInfoVo>> getTalkDetailById(@RequestParam Long id) {
        List<TalkInfoVo>List=service.getTalkDetailByid(id);
        return Result.ok(List);
    }
    @Operation(summary = "根据公寓id分页查询房间列表")
    @GetMapping("pageItemByApartmentId")
    public Result<IPage<RoomItemVo>> pageItemByApartmentId(@RequestParam long current, @RequestParam long size, @RequestParam Long id) {
        Page<RoomItemVo> roomItemVoPage = new Page<>(current,size);
        IPage<RoomItemVo>result=service.pageItemByApartmentId(roomItemVoPage,id);
        return Result.ok(result);
    }
}
