package com.zt.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zt.blog.common.constant.StatusCode;
import com.zt.blog.common.entity.Result;
import com.zt.blog.model.Article;
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

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-03
 */
@RestController
@RequestMapping("blog/article")
@Api(description = "文章相关api")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @ApiOperation("获取文章列表，分页显示")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNo",value = "第几页",paramType = "query",defaultValue = "1"),
        @ApiImplicitParam(name = "pageSize",value = "每页显示数量",paramType = "query",defaultValue = "10")
    })
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Result<Page<Article>> getList(Integer pageNo,Integer pageSize){
        Result<Page<Article>> result=new Result<>(true,StatusCode.Status.SUCCESS);
        Page<Article> page=new Page<>(pageNo,pageSize);
        articleService.selectPage(page, new QueryWrapper<Article>().lambda()
                .orderByDesc(Article::getVersion));
        result.setData(page);
        return result;
    }




    @ApiOperation(value = "根据id获取文章详情")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",value = "文章id",required = true,paramType = "path")
    })
    @RequestMapping(value = "/detail/{id}",method = RequestMethod.GET)
    public Result<Article> getDetail(@PathVariable("id") Integer id){
        Result<Article> result=new Result<>(true,StatusCode.Status.SUCCESS);
        Article article = articleService.selectById(id);
        result.setData(article);
        return result;
    }

    @ApiOperation(value = "发布新文章")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "title",value = "文章标题",required = true,paramType = "query"),
        @ApiImplicitParam(name = "content",value = "文章内容",required = true,paramType = "query"),
        @ApiImplicitParam(name = "categoryId",value = "分类id",required = true,paramType = "query")
    })
    @RequestMapping(value = "/publish",method = RequestMethod.POST)
    public Result publish(Article article){
        Result result=new Result<>();
        boolean insert = articleService.insert(article);
        result.setSuccess(insert);
        return result;
    }




}

