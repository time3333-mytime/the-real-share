package com.WHY.lease.web.admin.mapper;

import com.WHY.lease.model.entity.ViewAppointment;
import com.WHY.lease.web.admin.vo.appointment.AppointmentQueryVo;
import com.WHY.lease.web.admin.vo.appointment.AppointmentVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
* @author liubo
* @description 针对表【view_appointment(预约看房信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.atguigu.lease.model.ViewAppointment
*/
public interface ViewAppointmentMapper extends BaseMapper<ViewAppointment> {

    IPage<AppointmentVo> pageAppointmentVo(Page<AppointmentVo> page, AppointmentQueryVo queryVo);
}




