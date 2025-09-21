package com.WHY.lease.web.app.controller.agreement;

import com.WHY.lease.common.login.LoginUserHolder;
import com.WHY.lease.common.result.Result;
import com.WHY.lease.model.entity.LeaseAgreement;
import com.WHY.lease.model.enums.LeaseStatus;
import com.WHY.lease.web.app.service.LeaseAgreementService;
import com.WHY.lease.web.app.vo.agreement.AgreementDetailVo;
import com.WHY.lease.web.app.vo.agreement.AgreementItemVo;
import com.WHY.lease.web.app.vo.pay.AliPayFaceToFaceModel;
import com.WHY.lease.web.app.vo.pay.AliPayResultModel;
import com.WHY.lease.web.app.vo.pay.PayUtils;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/agreement")
@Tag(name = "租约信息")
@AllArgsConstructor
public class LeaseAgreementController {

    private final LeaseAgreementService service;

    private final PayUtils payUtils;

    @Operation(summary = "获取个人租约基本信息列表")
    @GetMapping("listItem")
    public Result<List<AgreementItemVo>> listItem() {
        String phone = LoginUserHolder.getLoginUser().getUsername();
        System.out.println(phone);
        List<AgreementItemVo> list=service.listItemByPhone(phone);
        return Result.ok(list);
    }

    @Operation(summary = "根据id获取租约详细信息")
    @GetMapping("getDetailById")
    public Result<AgreementDetailVo> getDetailById(@RequestParam Long id) {
        AgreementDetailVo agreementDetailVo = service.getDetailById(id);
        return Result.ok(agreementDetailVo);
    }

    @Operation(summary = "支付宝支付", description = "支付宝支付")
    @GetMapping("toPay")
    public Result<String> updateStatusToPay(@RequestParam Long id) {
        AgreementDetailVo leaseAgreement = service.getDetailById(id);
        Object data = payUtils.aliPayPreorder(new AliPayFaceToFaceModel(String.valueOf(leaseAgreement.getId()), "租金缴纳", String.valueOf(leaseAgreement.getRent()), leaseAgreement.getAdditionalInfo())).getData();
        AliPayResultModel aliPayResultModel=null;
        if(data!=null && data instanceof AliPayResultModel){
            aliPayResultModel =(AliPayResultModel)data;
        }
        if(aliPayResultModel==null){
            return Result.fail(400,"支付生成失败");
        }
        return Result.ok(aliPayResultModel.getQrCode());
    }

    @Operation(summary = "根据id更新租约状态", description = "用于确认租约")
    @PostMapping("updateStatusById")
    public Result updateStatusById(@RequestParam Long id, @RequestParam LeaseStatus leaseStatus) {
        LambdaUpdateWrapper<LeaseAgreement> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(LeaseAgreement::getId, id);
        updateWrapper.set(LeaseAgreement::getStatus, leaseStatus);
        service.update(updateWrapper);
        return Result.ok();
    }

    @Operation(summary = "保存或更新租约", description = "用于续约")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody LeaseAgreement leaseAgreement) {
        service.saveOrUpdate(leaseAgreement);
        return Result.ok();
    }

}
