package com.zt.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zt.blog.common.constant.Constants;
import com.zt.blog.common.entity.Result;
import com.zt.blog.common.util.Md5Encrypt;
import com.zt.blog.common.util.SessionUtil;
import com.zt.blog.common.util.VerifyCodeUtil;
import com.zt.blog.model.User;
import com.zt.blog.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-03
 */
@RestController
@RequestMapping("/")
@Api(description = "用户相关api")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取用户信息")
    @RequestMapping(value = "/user/getUserInfo",method = RequestMethod.GET)
    public Result<User> getUserInfo(){
        Result<User> result=new Result<>(true,Constants.Status.SUCCESS);
        User user = SessionUtil.getSessionUser();
        result.setData(user);
        return result;
    }

    @ApiOperation("用户注册")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userAccount",value = "账号",required = true),
        @ApiImplicitParam(name = "nickName",value = "昵称",required = true),
        @ApiImplicitParam(name = "password",value = "密码",required = true)
    })
    @RequestMapping(value = "/registry",method = RequestMethod.POST)
    public Result register(String userAccount,String nickName,String password) {
        if (StringUtils.isEmpty(userAccount)||StringUtils.isEmpty(nickName)||StringUtils.isEmpty(password)){
            return new Result(Constants.Status.PARAM_EMPTY);
        }
        String uPattern = "^[a-zA-Z0-9_]{2,16}$";
        if (!Pattern.matches(uPattern,userAccount)) {
            return new Result(Constants.Status.PARAM_ERROR);
        }
        User register = userService.getOne(new QueryWrapper<User>().lambda().eq(User::getUserAccount, userAccount));
        if (null != register){
            return new Result(Constants.Status.USER_HAS_EXISTS);
        }
        Result result=null;
        User user=new User();
        user.setUserAccount(userAccount);
        user.setNickName(nickName);
        String checkCode = VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_ALL_MIXED, 6, null);
        user.setCheckCode(checkCode);
        user.setPassword(Md5Encrypt.md5(password+checkCode));
        boolean insert = userService.save(user);
        if (insert){
            result=new Result(true,Constants.Status.SUCCESS);
        }else {
            result=new Result(Constants.Status.BUSINESS_ERROR);
        }
        return result;
    }


    @ApiOperation("用户登录")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userAccount",value = "账号",required = true),
        @ApiImplicitParam(name = "password",value = "密码",required = true)
    })
    @RequestMapping(value = "/userLogin",method = RequestMethod.POST)
    public Result<User> userLogin(String userAccount,String password){
        if (StringUtils.isEmpty(userAccount)||StringUtils.isEmpty(password)){
            return new Result<>(Constants.Status.PARAM_EMPTY);
        }
        Result<User> result=null;
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        //已登录
        if (subject.isAuthenticated()) {
            result=new Result<User>(true ,Constants.Status.SUCCESS);;
            return result;
        }
        //登录验证
        User login = userService.getOne(new QueryWrapper<User>().lambda().eq(User::getUserAccount, userAccount));
        if (null == login){
            result=new Result<User>(Constants.Status.USER_LOGIN_ERROR);
            return result;
        }
        String md5 = Md5Encrypt.md5(password + login.getCheckCode());
        if (!md5.equals(login.getPassword())){
            result=new Result<User>(Constants.Status.USER_LOGIN_ERROR);
            return result;
        }
        // 身份验证
        subject.login(new UsernamePasswordToken(userAccount, password));
        //登录成功
        session.setAttribute(Constants.SESSION_USER,login);
        result=new Result<User>(true ,Constants.Status.SUCCESS);
        result.setData(login);
        return result;
    }


    @ApiOperation("用户登出")
    @RequestMapping(value = "/userLogout",method = RequestMethod.POST)
    public Result userLogout(){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.removeAttribute(Constants.SESSION_USER);
        subject.logout();
        return new Result(true ,Constants.Status.SUCCESS);
    }

}

