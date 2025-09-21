package com.WHY.lease.web.app.vo.pay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "alipay")
public class AlipayConfig {

    //网关名称
    private String openApiDomain;
    private String mcloudApiDomain;
    //app id
    private String appId;
    // pid
    private String pid;
    //私钥
    private String privateKey;
    //公钥
    private String publicKey;
    //SHA256withRsa对应支付宝公钥
    private String alipayPublicKey;
    //签名类型: RSA->SHA1withRsa,RSA2->SHA256withRsa
    private String signType;
    //回调地址
    private String notifyUrl;
//    //回调地址
//    private String serverUrl;
//    //回调地址
//    private String format;
//    //回调地址
//    private String charset;
}