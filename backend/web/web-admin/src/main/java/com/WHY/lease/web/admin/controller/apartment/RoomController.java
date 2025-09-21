package com.WHY.lease.web.admin.controller.apartment;


import com.WHY.lease.common.result.Result;
import com.WHY.lease.model.entity.RoomInfo;
import com.WHY.lease.model.enums.ReleaseStatus;
import com.WHY.lease.web.admin.vo.room.RoomDetailVo;
import com.WHY.lease.web.admin.vo.room.RoomItemVo;
import com.WHY.lease.web.admin.vo.room.RoomQueryVo;
import com.WHY.lease.web.admin.vo.room.RoomSubmitVo;
import com.WHY.lease.web.admin.service.RoomInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "房间信息管理")
@RestController
@RequestMapping("/admin/room")
public class RoomController {

    @Autowired
    private RoomInfoService service;

    @Operation(summary = "保存或更新房间信息")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody RoomSubmitVo roomSubmitVo) {
        service.saveOrUpdateRoom(roomSubmitVo);
        return Result.ok();
    }

    @Operation(summary = "根据条件分页查询房间列表")
    @GetMapping("pageItem")
    public Result<IPage<RoomItemVo>> pageItem(@RequestParam long current, @RequestParam long size, RoomQueryVo queryVo) {
        Page<RoomItemVo> page =new Page<>(current,size);
        IPage<RoomItemVo>result=service.pageItem(page,queryVo);
        return Result.ok(result);
    }

    @Operation(summary = "根据id获取房间详细信息")
    @GetMapping("getDetailById")
    public Result<RoomDetailVo> getDetailById(@RequestParam Long id) {
        RoomDetailVo result=service.getDetailById(id);
        return Result.ok(result);
    }

    @Operation(summary = "根据id删除房间信息")
    @DeleteMapping("removeById")
    public Result removeById(@RequestParam Long id) {
        service.removeByRoomId(id);
        return Result.ok();
    }

    @Operation(summary = "根据id修改房间发布状态")
    @PostMapping("updateReleaseStatusById")
    public Result updateReleaseStatusById(Long id, ReleaseStatus status) {
        LambdaUpdateWrapper<RoomInfo> lambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(RoomInfo::getId,id);
        lambdaUpdateWrapper.set(RoomInfo::getIsRelease,status);
        service.update(lambdaUpdateWrapper);
        return Result.ok();
    }

    @GetMapping("listBasicByApartmentId")
    @Operation(summary = "根据公寓id查询房间列表")
    public Result<List<RoomInfo>> listBasicByApartmentId(Long id) {
        LambdaQueryWrapper<RoomInfo> roomInfoLambdaQueryWrapper=new LambdaQueryWrapper<>();
        roomInfoLambdaQueryWrapper.eq(RoomInfo::getApartmentId,id);
        roomInfoLambdaQueryWrapper.eq(RoomInfo::getIsRelease,ReleaseStatus.RELEASED);
        List<RoomInfo> list = service.list(roomInfoLambdaQueryWrapper);
        return Result.ok(list);
    }

}


















