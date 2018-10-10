package com.zt.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zt.blog.common.constant.StatusCode;
import com.zt.blog.common.entity.Result;
import com.zt.blog.common.util.SessionUtil;
import com.zt.blog.model.Article;
import com.zt.blog.model.User;
import com.zt.blog.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-03
 */
@RestController
@RequestMapping("/article")
@Api(description = "文章相关api")
public class ArticleController {


    @Autowired
    private ArticleService articleService;

    @ApiOperation("获取文章列表，分页显示")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNo",value = "第几页",defaultValue = "1"),
        @ApiImplicitParam(name = "pageSize",value = "每页显示数量",defaultValue = "10")
    })
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Result<Page<Article>> getList(Integer pageNo,Integer pageSize){
        Result<Page<Article>> result=new Result<>(true,StatusCode.Status.SUCCESS);
        if (null == pageNo){
            pageNo=1;
        }
        if (null == pageSize){
            pageNo=10;
        }
        Page<Article> page=new Page<>(pageNo,pageSize);
        articleService.selectPage(page, new QueryWrapper<Article>().lambda()
                .eq(Article::getShowMode,1) //公开
                .eq(Article::getArticleStatus,1)//已发布
                );
        result.setData(page);
        return result;
    }

    @ApiOperation(value = "根据id获取文章详情")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",value = "文章id",required = true,paramType = "path")
    })
    @RequestMapping(value = "/detail/{id}",method = RequestMethod.GET)
    public Result<Article> getDetail(@PathVariable("id") Integer id){
        if (null == id){
            return new Result<>(StatusCode.Status.PARAM_EMPTY);
        }
        Result<Article> result=new Result<>(true,StatusCode.Status.SUCCESS);
        Article article = articleService.selectById(id);
        result.setData(article);
        return result;
    }
    @ApiOperation("添加新文章")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "title",value = "文章标题",required = true),
        @ApiImplicitParam(name = "digest",value = "文章摘要"),
        @ApiImplicitParam(name = "content",value = "文章内容",required = true),
        @ApiImplicitParam(name = "categoryId",value = "分类id",required = true),
        @ApiImplicitParam(name = "tags",value = "文章标签，自己填写，多个用','分隔"),
        @ApiImplicitParam(name = "showMode",value = "展示方式(1：公开；2：私有)",required = true,defaultValue = "1"),
        @ApiImplicitParam(name = "articleStatus",value = "文章状态(0：编辑中；1：已发布)",defaultValue = "0")
    })
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(Article article){
        if (null == article.getTitle() ||null == article.getContent() ||null == article.getCategoryId()
                || null == article.getShowMode()){
            return new Result(StatusCode.Status.PARAM_EMPTY);
        }
        Result result=null;
        if (1 == article.getArticleStatus()){
            article.setPublishTime(new Date());//发布时间
        }
        User user = SessionUtil.getSessionUser();
        article.setUserId(user.getId());
        article.setUserName(user.getNickName());
        boolean insert = articleService.insert(article);
        if (insert){
            result=new Result(true,StatusCode.Status.SUCCESS);
        }else {
            result=new Result(StatusCode.Status.BUSINESS_ERROR);
        }
        return result;
    }


    @ApiOperation(value = "发布文章")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",value = "文章id",required = true),
    })
    @RequestMapping(value = "/publish",method = RequestMethod.POST)
    public Result publish(Article article){
        if (null == article.getId()){
            return new Result<>(StatusCode.Status.PARAM_EMPTY);
        }
        Result result=null;
        article.setPublishTime(new Date());//发布时间
        article.setArticleStatus(1);
        boolean insert = articleService.updateById(article);
        if (insert){
            result=new Result(true,StatusCode.Status.SUCCESS);
        }else {
            result=new Result(StatusCode.Status.BUSINESS_ERROR);
        }
        return result;
    }

    @ApiOperation("删除文章")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",value = "文章id",required = true)
    })
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public Result del(Integer id){
        if (null ==id ){
            return new Result(StatusCode.Status.PARAM_EMPTY);
        }
        articleService.deleteById(id);
        return new Result<>(true,StatusCode.Status.SUCCESS);
    }


}

