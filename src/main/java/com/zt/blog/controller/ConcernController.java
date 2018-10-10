package com.zt.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zt.blog.common.constant.StatusCode;
import com.zt.blog.common.entity.Result;
import com.zt.blog.common.util.SessionUtil;
import com.zt.blog.model.Concern;
import com.zt.blog.model.User;
import com.zt.blog.service.ConcernService;
import com.zt.blog.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 我的关注 前端控制器
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-03
 */
@RestController
@RequestMapping("/concern")
@Api(description = "我的关注 相关api")
public class ConcernController {

    @Autowired
    private ConcernService concernService;
    @Autowired
    private UserService userService;

    @ApiOperation("我的关注列表")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Result<List<Concern>> getList(){
        Result<List<Concern>> result=new Result<>(true,StatusCode.Status.SUCCESS);
        User user = SessionUtil.getSessionUser();
        List<Concern> concerns = concernService.selectList(new QueryWrapper<Concern>().lambda()
                .eq(Concern::getUserId, user.getId()));
        result.setData(concerns);
        return result;
    }


    @ApiOperation("新增关注")
    @ApiImplicitParam(name = "userId",value = "用户id",required = true)
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public Result add(Integer userId){
        Result result=null;
        if (null == userId){
            return new Result(StatusCode.Status.PARAM_EMPTY);
        }
        User own = SessionUtil.getSessionUser();
        User user = userService.selectById(userId);
        if (null == user){
            return new Result(StatusCode.Status.USER_NOT_EXISTS);
        }
        Concern concern=new Concern();
        concern.setConcernId(userId);
        concern.setConcernName(user.getNickName());
        concern.setUserId(own.getId());
        boolean insert = concernService.insert(concern);
        if (insert){
            result=new Result(true,StatusCode.Status.SUCCESS);
        }else {
            result=new Result(StatusCode.Status.BUSINESS_ERROR);
        }
        return result;
    }

    @ApiOperation("删除关注")
    @ApiImplicitParam(name = "id",required = true)
    @RequestMapping(value = "del",method = RequestMethod.POST)
    public Result del(Integer id){
        if (null ==id ){
            return new Result(StatusCode.Status.PARAM_EMPTY);
        }
        concernService.deleteById(id);
        return new Result<>(true,StatusCode.Status.SUCCESS);
    }

}

