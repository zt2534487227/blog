package com.zt.blog.common.constant;

/**
 * @Author: ZhouTian
 * @Description:
 * @Date: 2018/8/21
 */
public interface Constants {

    String SESSION_USER="z_user";

    String SESSION_VERIFYCODE="z_verifyCode";


    enum Status {
        /**
         * 成功
         */
        SUCCESS("00", "成功"),
        /**
         * 业务失败
         */
        BUSINESS_ERROR("01", "失败"),
         /**
          * 签名错误
          */
        SIGN_ERROR("02", "签名错误！"),
        /**
         * 解密失败
         */
        DECODE_ERROR("03","解密失败"),
        /**
         * 参数为空
         */
        PARAM_EMPTY("04","参数为空"),
        PARAM_ERROR("08","参数不合法"),
        /**
         * 服务器内部错误
         */
        SERVER_ERROR("05","服务器内部错误"),
         /**
         * 请求超时
         */
        TIMEOUT_ERROR("06", "请求超时"),
        /**
         * 验证码错误
         */
        CHECK_CODE_ERROR("07","验证码错误"),
        REQUEST_ERROR("08","请求错误"),
        /**
         * 用户未登录
         */
        USER_NOT_LOGIN("11","用户未登录"),
        USER_NOT_EXISTS("12","用户不存在"),
        /**
         * 用户登录失败
         */
        USER_LOGIN_ERROR("13","账号或密码错误"),
        USER_HAS_EXISTS("14","该账号已存在"),

        ARTICLE_NOT_EXISTS("21","文章不存在")
        ;
        private String code;
        private String msg;
        Status(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Status{");
            sb.append("code='").append(code).append('\'');
            sb.append(", msg='").append(msg).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
