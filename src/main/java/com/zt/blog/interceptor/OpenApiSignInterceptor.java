package com.zt.blog.interceptor;

import com.alibaba.fastjson.JSON;
import com.zt.blog.common.config.SpringContextHolder;
import com.zt.blog.common.constant.Constants;
import com.zt.blog.common.entity.Result;
import com.zt.blog.common.property.AppletProps;
import com.zt.blog.common.util.DateUtil;
import com.zt.blog.common.util.Md5Encrypt;
import com.zt.blog.common.util.ResponseUtils;
import com.zt.blog.model.UserToken;
import com.zt.blog.service.UserTokenService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author ZhouTian
 * @Description
 * @since  2018/11/6
 */
public class OpenApiSignInterceptor  implements HandlerInterceptor {

    private String loginUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String uri = request.getRequestURI();
            if (uri.startsWith(loginUrl)){//需要登录验证,判断token
                String token = request.getParameter("token");
                if (StringUtils.isEmpty(token)){
                    return printJson(response,Constants.Status.TOKEN_NOT_EXISTS);
                }
                UserTokenService userTokenService = SpringContextHolder.getBean(UserTokenService.class);
                UserToken userToken = userTokenService.getByToken(token);
                if (null == userToken){
                    return printJson(response,Constants.Status.TOKEN_NOT_EXISTS);
                }
                Date expireTime = userToken.getExpireTime();
                if (DateUtil.before(expireTime,new Date())){//token过期
                    return printJson(response,Constants.Status.TOKEN_EXPIRED);
                }
            }
            String sign = request.getParameter("sign");
            String timeStamp = request.getParameter("timeStamp");
            String appId = request.getParameter("appId");
            if (StringUtils.isEmpty(timeStamp)||StringUtils.isEmpty(sign)||StringUtils.isEmpty(appId)){
                return printJson(response,Constants.Status.PARAM_EMPTY);
            }
            Date date = DateUtil.getDate(timeStamp);
            Date oneMinAfter = DateUtils.addMinutes(new Date(), 1);
            Date oneMinBefore = DateUtils.addMinutes(new Date(), -1);
            if (date.before(oneMinBefore)||date.after(oneMinAfter)){//时间搓失效 超过一分钟
                return printJson(response,Constants.Status.TIMESTAMP_INVALID);
            }
            //判断签名是否正确
            String signKey=AppletProps.getSignSecret() +"&@!"+timeStamp+"#*("+appId;
            String signMd5 = Md5Encrypt.md5(signKey);
            if (!signMd5.equalsIgnoreCase(sign)) {
                return printJson(response,Constants.Status.SIGN_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 输出状态信息
     * @param response
     * @param status
     * @return
     */
    private boolean printJson(HttpServletResponse response, Constants.Status status) {
        Result result = new Result(status);
        ResponseUtils.renderJson(response, JSON.toJSONString(result));
        return false;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

}
