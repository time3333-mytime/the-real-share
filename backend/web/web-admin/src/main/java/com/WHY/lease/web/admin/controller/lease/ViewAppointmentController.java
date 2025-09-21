package com.WHY.lease.web.admin.controller.lease;


import com.WHY.lease.common.result.Result;
import com.WHY.lease.model.entity.ViewAppointment;
import com.WHY.lease.model.enums.AppointmentStatus;
import com.WHY.lease.web.admin.vo.appointment.AppointmentQueryVo;
import com.WHY.lease.web.admin.vo.appointment.AppointmentVo;
import com.WHY.lease.web.admin.service.ViewAppointmentService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Tag(name = "预约看房管理")
@RequestMapping("/admin/appointment")
@RestController
public class ViewAppointmentController {

    @Autowired
    private ViewAppointmentService service;

    @Operation(summary = "分页查询预约信息")
    @GetMapping("page")
    public Result<IPage<AppointmentVo>> page(@RequestParam long current, @RequestParam long size, AppointmentQueryVo queryVo) {
        Page<AppointmentVo> page = new Page<>();
        IPage<AppointmentVo> result = service.pageAppointmentVo(page,queryVo);
        return Result.ok(result);
    }

    @Operation(summary = "根据id更新预约状态")
    @PostMapping("updateStatusById")
    public Result updateStatusById(@RequestParam Long id, @RequestParam AppointmentStatus status) {
        LambdaUpdateWrapper<ViewAppointment> viewAppointmentLambdaQueryWrapper=new LambdaUpdateWrapper<>();
        viewAppointmentLambdaQueryWrapper.eq(ViewAppointment::getId,id);
        viewAppointmentLambdaQueryWrapper.set(ViewAppointment::getAppointmentStatus,status);
        service.update(viewAppointmentLambdaQueryWrapper);
        return Result.ok();
    }

}
