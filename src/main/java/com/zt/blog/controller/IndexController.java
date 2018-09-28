package com.zt.blog.controller;

import com.zt.blog.common.constant.StatusCode;
import com.zt.blog.common.entity.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZhouTian
 * @Description:
 * @Date: 2018/9/3
 */
@RestController
@RequestMapping("/")
public class IndexController {

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(){
        return "welcome to my blog";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public Result login(){
        return new Result(StatusCode.Status.USER_NOT_LOGIN);
    }

    @RequestMapping(value = "/403",method = RequestMethod.GET)
    public String unAuth(){
        return "403";
    }
    @RequestMapping(value = "/404",method = RequestMethod.GET)
    public String notFound(){
        return "404";
    }
    @RequestMapping(value = "/500",method = RequestMethod.GET)
    public String serverError(){
        return "500";
    }

}
