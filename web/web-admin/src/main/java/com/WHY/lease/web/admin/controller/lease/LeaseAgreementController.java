package com.WHY.lease.web.admin.controller.lease;


import com.WHY.lease.common.result.Result;
import com.WHY.lease.model.entity.LandloadRoom;
import com.WHY.lease.model.entity.LeaseAgreement;
import com.WHY.lease.model.entity.UserInfo;
import com.WHY.lease.model.enums.LeaseStatus;
import com.WHY.lease.web.admin.mapper.LeaseAgreementMapper;
import com.WHY.lease.web.admin.service.LandloadRoomService;
import com.WHY.lease.web.admin.service.RoomInfoService;
import com.WHY.lease.web.admin.service.UserInfoService;
import com.WHY.lease.web.admin.vo.agreement.AgreementQueryVo;
import com.WHY.lease.web.admin.vo.agreement.AgreementVo;
import com.WHY.lease.web.admin.service.LeaseAgreementService;
import com.WHY.lease.web.admin.vo.room.RoomDetailVo;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;


@Tag(name = "租约管理")
@RestController
@RequestMapping("/admin/agreement")
public class LeaseAgreementController {
    @Autowired
    private JavaMailSender sender;
    @Autowired
    private LeaseAgreementService service;
    @Autowired
    private LeaseAgreementMapper mapper;
    @Autowired
    private RoomInfoService roomInfoService;
    @Autowired
    private UserInfoService userInfoService;
    @Operation(summary = "保存或修改租约信息")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody LeaseAgreement leaseAgreement) {
        service.saveOrUpdate(leaseAgreement);
        return Result.ok();
    }

    @Operation(summary = "根据条件分页查询租约列表")
    @GetMapping("page")
    public Result<IPage<AgreementVo>> page(@RequestParam long current, @RequestParam long size, AgreementQueryVo queryVo) {
        Page<AgreementVo> page = new Page<>(current,size);
        IPage<AgreementVo> result=service.pageAgreement(page,queryVo);
        return Result.ok(result);
    }

    @Operation(summary = "根据id查询租约信息")
    @GetMapping(name = "getById")
    public Result<AgreementVo> getById(@RequestParam Long id) {
        AgreementVo result= service.getAgreementById(id);
        return Result.ok(result);
    }

    @Operation(summary = "根据id删除租约信息")
    @DeleteMapping("removeById")
    public Result removeById(@RequestParam Long id) {
        service.removeById(id);
        return Result.ok();
    }

    @Operation(summary = "根据id更新租约状态")
    @PostMapping("updateStatusById")
    public Result updateStatusById(@RequestParam Long id, @RequestParam LeaseStatus status) {
        LambdaUpdateWrapper<LeaseAgreement> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(LeaseAgreement::getId, id);
        updateWrapper.set(LeaseAgreement::getStatus, status);
        service.update(updateWrapper);
        LeaseAgreement leaseAgreement = mapper.selectById(id);
        RoomDetailVo detailById = roomInfoService.getDetailById(leaseAgreement.getRoomId());
        UserInfo byId = userInfoService.getById(detailById.getLandlordId());
        if(status==LeaseStatus.SIGNED){
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("关于您的房租");
            message.setText("尊敬的用户您好，您的房间,id为"+leaseAgreement.getRoomId()+"已经租出 房租已到账");
            message.setTo(byId.getPhone());
            message.setFrom();//填你自己的
            sender.send(message);
        }
        return Result.ok();
    }

}

