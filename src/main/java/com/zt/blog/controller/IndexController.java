package com.zt.blog.controller;

import com.google.common.collect.Sets;
import com.zt.blog.common.constant.Constants;
import com.zt.blog.common.entity.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * @Author: ZhouTian
 * @Description:
 * @Date: 2018/9/3
 */
@Controller
@RequestMapping("/")
public class IndexController {

    private static final Set<String> blogers=Sets.newHashSet("zt.zhendehenyouyisi.com","zjy.zhendehenyouyisi.com","cp.zhendehenyouyisi.com","cj.zhendehenyouyisi.com");

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(HttpServletRequest request){
        Result<String> result=new Result<>(true,Constants.Status.SUCCESS);
        String bloger="my";
        String serverName = request.getServerName();
        if (blogers.contains(serverName)){
            bloger=serverName.substring(0,serverName.indexOf("."));
        }
        result.setData("welcome to "+bloger+" blog");
        return "index";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    @ResponseBody
    public Result login(){
        return new Result(Constants.Status.USER_NOT_LOGIN);
    }

    @RequestMapping(value = "/403",method = RequestMethod.GET)
    @ResponseBody
    public String unAuth(){
        return "403";
    }

    @RequestMapping(value = "/404",method = RequestMethod.GET)
    public String notFound(){
        return "index";
    }

    @RequestMapping(value = "/500",method = RequestMethod.GET)
    @ResponseBody
    public String serverError(){
        return "500";
    }

}
