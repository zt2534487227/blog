package com.zt.blog.controller;


import com.zt.blog.common.constant.StatusCode;
import com.zt.blog.common.entity.Result;
import com.zt.blog.model.Concern;
import com.zt.blog.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 我的关注 前端控制器
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-03
 */
@RestController
@RequestMapping("/blog/concern")
@Api(description = "我的关注 相关api")
public class ConcernController {


    @ApiOperation("我的关注列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Result<Concern> getList(){
        Result<Concern> result=new Result<>(true,StatusCode.Status.SUCCESS);



        return result;
    }


    @ApiOperation("新增关注")
    @ApiImplicitParam(name = "userId",value = "用户id",required = true)
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public Result add(Integer userId){
        Result result=new Result(StatusCode.Status.SUCCESS);
        if (null != userId){
            User user = new User();
            user=user.selectById(userId);
            Concern concern=new Concern();
            concern.setConcernId(userId);
            concern.setConcernName(user.getNickName());
            concern.setUserId(1);
            boolean insert = concern.insert();
            result.setSuccess(insert);
        }
        return result;
    }

    @ApiOperation("删除关注")
    @ApiImplicitParam(name = "id",required = true)
    @RequestMapping(value = "del",method = RequestMethod.POST)
    public Result del(Integer id){
        Result result=new Result(true,StatusCode.Status.SUCCESS);
        if (null != id){
            Concern concern=new Concern();
            concern.deleteById(id);
        }
        return result;
    }

}

