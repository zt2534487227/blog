package com.zt.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zt.blog.common.constant.Constants;
import com.zt.blog.common.entity.Result;
import com.zt.blog.common.util.SessionUtil;
import com.zt.blog.model.Article;
import com.zt.blog.model.Collection;
import com.zt.blog.model.User;
import com.zt.blog.service.ArticleService;
import com.zt.blog.service.CollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 我的收藏 前端控制器
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-03
 */
@RestController
@RequestMapping("/collection")
@Api(description = "收藏相关api")
public class CollectionController {


    @Autowired
    private CollectionService collectionService;

    @Autowired
    private ArticleService articleService;


    @ApiOperation("我的收藏列表")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Result<List<Collection>> getList(HttpServletRequest request){
        User user = SessionUtil.getSessionUser();
        if (null == user){
            return new Result<>(Constants.Status.USER_NOT_LOGIN);
        }
        Result<List<Collection>> result=new Result<>(true,Constants.Status.SUCCESS);
        List<Collection> collections = collectionService.list(new QueryWrapper<Collection>().lambda()
                .eq(Collection::getUserId, user.getId()));
        result.setData(collections);
        return result;
    }


    @ApiOperation("将文章id加入收藏")
    @ApiImplicitParam(name = "articleId",value = "文章id",required = true)
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(Integer articleId){
        Result result=null;
        if (null ==articleId){
           return new Result(Constants.Status.PARAM_EMPTY);
        }
        User user = SessionUtil.getSessionUser();
        Article article = articleService.getById(articleId);
        if (null == article){
            return new Result(Constants.Status.ARTICLE_NOT_EXISTS);
        }
        Collection collection=new Collection();
        collection.setArticleId(articleId);
        collection.setUserId(user.getId());
        collection.setArticleTitle(article.getTitle());
        collection.setArticleAuthor(article.getUserName());
        boolean insert = collectionService.save(collection);
        if (insert){
            result=new Result(true,Constants.Status.SUCCESS);
        }else {
            result=new Result(Constants.Status.BUSINESS_ERROR);
        }
        return result;
    }

    @ApiOperation("根据id删除收藏")
    @ApiImplicitParam(name = "id",required = true)
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public Result del(Integer id){
        if (null ==id ){
            return new Result(Constants.Status.PARAM_EMPTY);
        }
        collectionService.removeById(id);
        return new Result<>(true,Constants.Status.SUCCESS);
    }

}

