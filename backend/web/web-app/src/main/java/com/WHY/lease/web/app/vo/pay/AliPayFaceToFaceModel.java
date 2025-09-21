package com.WHY.lease.web.app.vo.pay;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AliPayFaceToFaceModel {
    private String outTradeNo;
    private String subject;
    private String totalAmount;
    private String body;
}
