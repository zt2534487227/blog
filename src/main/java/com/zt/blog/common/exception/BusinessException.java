package com.zt.blog.common.exception;

/**
 * @Author: zhoutian
 * @Description:  业务逻辑异常
 * @Date: 2018/9/11
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
