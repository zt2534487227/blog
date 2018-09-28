package com.zt.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zt.blog.common.constant.StatusCode;
import com.zt.blog.common.entity.Result;
import com.zt.blog.common.util.Md5Encrypt;
import com.zt.blog.common.util.VerifyCodeUtil;
import com.zt.blog.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
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
@RequestMapping("/")
@Api(description = "用户相关api")
public class UserController {

    @ApiOperation(value = "根据id获取用户信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",value = "用户id",required = true)
    })
    @RequestMapping(value = "/user/getUserInfo",method = RequestMethod.GET)
    public Result<User> getUserInfo(Integer id){
        Result<User> result=new Result<>(true,StatusCode.Status.SUCCESS);
        User user = new User();
        user=user.selectById(id);
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
    public Result register(User user) {
        User register = user.selectOne(new QueryWrapper<User>().lambda().eq(User::getUserAccount, user.getUserAccount()));
        if (null != register){
            return new Result(StatusCode.Status.USER_HAS_EXISTS);
        }
        String checkCode = VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_ALL_MIXED, 6, null);
        user.setCheckCode(checkCode);
        user.setPassword(Md5Encrypt.md5(user.getPassword()+checkCode));
        boolean insert = user.insert();
        return new Result(insert ,StatusCode.Status.SUCCESS);
    }




    @ApiOperation("用户登录")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userAccount",value = "账号",required = true),
        @ApiImplicitParam(name = "password",value = "密码",required = true)
    })
    @RequestMapping(value = "/userLogin",method = RequestMethod.POST)
    public Result<User> userLogin(User user){
        Result<User> result=null;
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        //已登录
        if (subject.isAuthenticated()) {
            result=new Result<User>(true ,StatusCode.Status.SUCCESS);;
            return result;
        }
        //登录验证
        User login = user.selectOne(new QueryWrapper<User>().lambda().eq(User::getUserAccount, user.getUserAccount()));
        if (null == login){
            result=new Result<User>(StatusCode.Status.USER_LOGIN_ERROR);
            return result;
        }
        String md5 = Md5Encrypt.md5(user.getPassword() + login.getCheckCode());
        if (!md5.equals(login.getPassword())){
            result=new Result<User>(StatusCode.Status.USER_LOGIN_ERROR);
            return result;
        }
        // 身份验证
        subject.login(new UsernamePasswordToken(user.getUserAccount(), user.getPassword()));
        //登录成功
        session.setAttribute("user",login);
        result=new Result<User>(true ,StatusCode.Status.SUCCESS);
        result.setData(login);
        return result;
    }


    @ApiOperation("用户登出")
    @RequestMapping(value = "/userLogout",method = RequestMethod.POST)
    public Result userLogout(){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.removeAttribute("user");
        subject.logout();
        return new Result(true ,StatusCode.Status.SUCCESS);
    }

}

