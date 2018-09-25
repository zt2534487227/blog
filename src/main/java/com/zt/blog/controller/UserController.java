package com.zt.blog.controller;


import com.zt.blog.common.entity.Result;
import com.zt.blog.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-03
 */
@RestController
@RequestMapping("/blog/user")
@Api(description = "用户相关api")
public class UserController {

    @ApiOperation(value = "根据id获取用户信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",value = "用户id",required = true)
    })
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.GET)
    public Result<User> getUserInfo(Integer id){
        Result<User> result=new Result<>();
        User user = new User();
        user=user.selectById(id);
        result.setData(user);
        return result;
    }

    @ApiOperation(value = "判断用户是否登录")
    @RequestMapping(value = "/checkLogin",method = RequestMethod.GET)
    public Result checkLogin(){
        return null;
    }

    @ApiOperation("用户注册")
    @RequestMapping(value = "/registry",method = RequestMethod.POST)
    public Result register(){
        return null;
    }

    @ApiOperation("用户登录")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(){
        return null;
    }


}

