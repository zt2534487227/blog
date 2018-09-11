package com.zt.blog.common.exception;

/**
 * @Author: ZhouTian
 * @Description: dao 层异常
 * @Date: 2018/9/11
 */
public class DaoException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public DaoException(String message) {
        super(message);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
