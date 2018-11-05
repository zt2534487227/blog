package com.zt.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zt.blog.common.constant.Constants;
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
    public Result<List<Comment>> getMyComment(){
        Result<List<Comment>> result=new Result<>(true,Constants.Status.SUCCESS);
        User user = SessionUtil.getSessionUser();
        List<Comment> comments = commentService.list(new QueryWrapper<Comment>().lambda()
                .eq(Comment::getBloggerId, user.getId()).orderByDesc(Comment::getCreateTime));
        result.setData(comments);
        return result;
    }

    @ApiOperation("获取文章的评论")
    @ApiImplicitParam(name = "articleId",required = true)
    @RequestMapping(value = "/blogComment",method = RequestMethod.POST)
    public Result getArticleComment(Integer articleId){
        if (null == articleId){
            return new Result(Constants.Status.PARAM_EMPTY);
        }
        commentService.list(new QueryWrapper<Comment>().lambda()
                .eq(Comment::getArticleId,articleId).eq(Comment::getParentId,0));

        return null;
    }

    public Result getArticleReply(Integer commentId){
        if (null == commentId){
            return new Result(Constants.Status.PARAM_EMPTY);
        }

        commentService.list(new QueryWrapper<Comment>().lambda()
                .eq(Comment::getParentId,commentId));

        return null;
    }


    @ApiOperation("添加评论")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(Integer blogId){
        if (null == blogId){
            return new Result(Constants.Status.PARAM_EMPTY);
        }


        return null;
    }

    @ApiOperation("删除评论")
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public Result del(Integer id){
        if (null ==id ){
            return new Result(Constants.Status.PARAM_EMPTY);
        }
        commentService.removeById(id);
        return new Result<>(true,Constants.Status.SUCCESS);
    }

}

