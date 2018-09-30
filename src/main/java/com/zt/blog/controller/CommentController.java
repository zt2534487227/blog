package com.zt.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zt.blog.common.constant.StatusCode;
import com.zt.blog.common.entity.Result;
import com.zt.blog.common.util.SessionUtil;
import com.zt.blog.model.Comment;
import com.zt.blog.model.User;
import com.zt.blog.service.CommentService;
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
 * 评论表 前端控制器
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-03
 */
@RestController
@RequestMapping("/comment")
@Api(description = "评论相关 api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation("获取我的评论")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public Result getMyComment(){
        User user = SessionUtil.getSessionUser();
        List<Comment> comments = commentService.selectList(new QueryWrapper<Comment>().lambda()
                .eq(Comment::getBloggerId, user.getId()));

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
    public Result del(Integer id){
        if (null != id){
            commentService.deleteById(id);
        }
        return new Result(true,StatusCode.Status.SUCCESS);
    }

}

