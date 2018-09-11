package com.zt.blog.common.exception;

/**
 * @Author: ZhouTian
 * @Description: 系统异常
 * @Date: 2018/9/11
 */
public class SystemException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public SystemException(String message) {
        super(message);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }
}


