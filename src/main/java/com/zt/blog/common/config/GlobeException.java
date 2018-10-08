package com.zt.blog.common.config;

import com.zt.blog.common.constant.StatusCode;
import com.zt.blog.common.entity.Result;
import com.zt.blog.common.exception.BusinessException;
import com.zt.blog.common.exception.DaoException;
import com.zt.blog.common.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: ZhouTian;
 * @Description:
 * @Date: 2018/10/5
 */
@ControllerAdvice
public class GlobeException {

    private static final Logger log=LoggerFactory.getLogger(GlobeException.class);

    @ExceptionHandler({BusinessException.class,DaoException.class})
    @ResponseBody
    public Result businessException(HttpServletRequest request,Exception e){
        log.error("requestUrl:"+request.getRequestURI()+",ParameterMap:"+request.getParameterMap(),e);
        return new Result(StatusCode.Status.BUSINESS_ERROR);
    }

    @ExceptionHandler({SystemException.class,Exception.class})
    @ResponseBody
    public Result handException(HttpServletRequest request,Exception e){
        log.error("requestUrl:"+request.getRequestURI()+",ParameterMap:"+request.getParameterMap(),e);
        return new Result(StatusCode.Status.SERVER_ERROR);
    }



}
