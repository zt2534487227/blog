package com.zt.blog.controller;


import com.zt.blog.common.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-03
 */
@Controller
@RequestMapping("/blog/comment")
@Api(description = "评论相关 api")
public class CommentController {


    @ApiOperation("获取我的评论")
    @ApiImplicitParam(name = "userId",required = true)
    @RequestMapping(value = "/",method = RequestMethod.POST)
    public Result getMyComment(Integer userId){


        return null;
    }

    @ApiOperation("获取博客的评论")
    @ApiImplicitParam(name = "blogId",required = true)
    @RequestMapping(value = "/a",method = RequestMethod.POST)
    public Result getBlogComment(Integer blogId){


        return null;
    }


    @ApiOperation("添加评论")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(){

        return null;
    }

    @ApiOperation("删除评论")
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public Result del(){

        return null;
    }

}

