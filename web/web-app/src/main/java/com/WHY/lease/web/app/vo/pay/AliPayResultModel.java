package com.WHY.lease.web.app.vo.pay;

import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import lombok.Data;

@Data
public class AliPayResultModel {
    //商户订单号
    private String outTradeNo;
    //支付 二维码地址
    private String qrCode;
    //支付宝交易号
    private String tradeNo;
    /**
     *交易状态
     * WAIT_BUYER_PAY（交易创建，等待买家付款）
     * TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）
     * TRADE_SUCCESS（交易支付成功）
     * TRADE_FINISHED（交易结束，不可退款）
     * 状态业务说明
     * 1. 交易创建成功后，用户支付成功，交易状态转为 TRADE_SUCCESS（交易成功）。
     * 2. 交易成功后，规定退款时间内没有退款，交易状态转为 TRADE_FINISHED（交易完成）。
     * 3. 交易支付成功后，交易部分退款，交易状态为 TRADE_SUCCESS（交易成功）。
     * 4. 交易成功后，交易全额退款，交易状态转为 TRADE_CLOSED（交易关闭）。
     * 5. 交易创建成功后，用户未付款交易超时关闭，交易状态转为 TRADE_CLOSED（交易关闭）。
     * 6. 交易创建成功后，用户支付成功后，若用户商品不支持退款，交易状态直接转为 TRADE_FINISHED（交易完成）。
     * 注意：交易成功后部分退款，交易状态仍为 TRADE_SUCCESS（交易成功），如果一直部分退款退完所有交易金额则交易状态转为 TRADE_CLOSED（交易关闭），如果未退完所有交易金额，超过有效退款时间后交易状态转为 TRADE_FINISHED（交易完成）不可退款
     */
    private String tradeStatus;
    //交易金额
    private String totalAmount;
    //退款金额
    private String refundFee;
    //  本次退款是否发生了资金变化 Y: 资金发生变化，退款成功
    private String fundChange;
    //回调时返回请求传递时的body信息
    private String body;
}
