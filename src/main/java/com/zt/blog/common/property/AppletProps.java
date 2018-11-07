package com.zt.blog.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: ZhouTian
 * @Description:
 * @Date: 2018/11/6
 */
@Data
@ConfigurationProperties(prefix = "")
@Component
public class AppletProps {
    private String appId;
    private String secret;
    private String grantType="authorization_code";
    private String url="https://api.weixin.qq.com/sns/jscode2session";
    /**
     * 签名秘钥
     */
    private static String signSecret;

    public static String getSignSecret() {
        return signSecret;
    }

    public void setSignSecret(String signSecret) {
        AppletProps.signSecret = signSecret;
    }
}
