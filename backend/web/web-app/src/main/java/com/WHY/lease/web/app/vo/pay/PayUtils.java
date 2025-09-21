package com.WHY.lease.web.app.vo.pay;


import com.alipay.api.*;

import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.WHY.lease.common.result.Result;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PayUtils {
    private final AlipayConfig aliPayConfig;

    public AlipayClient getAlipayClient(){
        return new DefaultAlipayClient(
                aliPayConfig.getOpenApiDomain(),
                aliPayConfig.getAppId(),
                aliPayConfig.getPrivateKey(),
                AlipayConstants.FORMAT_JSON,
                AlipayConstants.CHARSET_UTF8,
                aliPayConfig.getAlipayPublicKey(),
                aliPayConfig.getSignType()
        );
    }
    private AlipayTradePrecreateRequest getTradePreCreateRequest(AliPayFaceToFaceModel aliPayFaceToFaceModel) {
        //实例化具体API对应的request类，类名称和接口名称对应,当前调用接口名称：alipay.trade.precreate（统一收单线下交易预创建（扫码支付）
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();//设置业务参数
        model.setOutTradeNo(aliPayFaceToFaceModel.getOutTradeNo());//商户订单号，商户自定义，需保证在商户端不重复，如：20200612000001
        model.setSubject(aliPayFaceToFaceModel.getSubject());//订单标题
        model.setTotalAmount(aliPayFaceToFaceModel.getTotalAmount());//订单金额，精确到小数点后两位
        model.setBody(aliPayFaceToFaceModel.getBody());//订单描述

        request.setBizModel(model);
        /*
         异步通知地址，以http或者https开头的，商户外网可以post访问的异步地址，用于接收支付宝返回的支付结果，如果未收到该通知可参考该文档进行确认：https://opensupport.alipay.com/support/helpcenter/193/201602475759
         */
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());

        return request;
    }
    /**
     * 支付宝预下单
     */
    public Result aliPayPreorder(AliPayFaceToFaceModel aliPayFaceToFaceModel) {
        try {
            AlipayClient alipayClient = getAlipayClient();
            AlipayTradePrecreateRequest tradePreCreateRequest = getTradePreCreateRequest(aliPayFaceToFaceModel);
            AlipayTradePrecreateResponse response = alipayClient.execute(tradePreCreateRequest);
            // System.out.println(response.getBody());
            if(StringUtils.equalsAny(response.getCode(),"10000")){
                AliPayResultModel resultModel=new AliPayResultModel();
                resultModel.setOutTradeNo(response.getOutTradeNo());
                resultModel.setQrCode(response.getQrCode());
                return Result.ok(resultModel);
            }else{
                return Result.fail(400,"获取支付二维码失败，错误信息："+response.getSubMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(400,"下单失败");
        }
    }
    /**
     * 交易状态查询
     * 可以查看以下帮助文档:
     * 判断交易是否成功：https://opensupport.alipay.com/support/helpcenter/195/201602516393?ant_source=zsearch
     * 状态ACQ.TRADE_NOT_EXIST（交易不存在）https://opensupport.alipay.com/support/helpcenter/89/201602475600?ant_source=zsearch
     */
    public Result queryTrade(AliPayFaceToFaceModel aliPayFaceToFaceModel) {
        try {
            AlipayClient alipayClient = getAlipayClient();
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();//实例化具体API对应的request类，类名称和接口名称对应,当前调用接口名称：alipay.trade.query（统一收单线下交易查询）
            AlipayTradeQueryModel model = new AlipayTradeQueryModel();
            // 注：交易号（TradeNo）与订单号（OutTradeNo）二选一传入即可，如果2个同时传入，则以交易号为准
            //支付接口传入的商户订单号。如：2020061601290011200000140004 **/
            model.setOutTradeNo(aliPayFaceToFaceModel.getOutTradeNo());
            // 异步通知/查询接口返回的支付宝交易号，如：2020061622001473951448314322 **/
            request.setBizModel(model);
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            if(response.isSuccess()){
                AliPayResultModel resultModel=new AliPayResultModel();
                resultModel.setOutTradeNo(response.getOutTradeNo());
                resultModel.setTradeStatus(response.getTradeStatus());
                resultModel.setTradeNo(response.getTradeNo());
                return Result.ok(resultModel);
            }else{
                return Result.fail(400,"支付宝订单查询失败："+response.getSubMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(400,"订单查询异常！");
        }
    }
}
