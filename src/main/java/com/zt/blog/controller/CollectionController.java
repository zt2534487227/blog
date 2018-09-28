package com.zt.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zt.blog.common.constant.BaseConstants;
import com.zt.blog.common.constant.StatusCode;
import com.zt.blog.common.entity.Result;
import com.zt.blog.model.Article;
import com.zt.blog.model.Collection;
import com.zt.blog.model.User;
import com.zt.blog.service.ArticleService;
import com.zt.blog.service.CollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
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
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user = (User) session.getAttribute(BaseConstants.SESSION_USER);
        Result<List<Collection>> result=new Result<>(true,StatusCode.Status.SUCCESS);
        List<Collection> collections = collectionService.selectList(new QueryWrapper<Collection>().lambda()
                .eq(Collection::getUserId, user.getId()));
        result.setData(collections);
        return result;
    }


    @ApiOperation("将文章id加入收藏")
    @ApiImplicitParam(name = "articleId",value = "文章id",required = true)
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(Integer articleId){
        Result result=new Result();
        if (null != articleId){
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            User user = (User) session.getAttribute(BaseConstants.SESSION_USER);
            Article article = articleService.selectById(articleId);
            if (null == article){
                return new Result(StatusCode.Status.ARTICLE_NOT_EXISTS);
            }
            Collection collection=new Collection();
            collection.setArticleId(articleId);
            collection.setUserId(user.getId());
            collection.setArticleTitle(article.getTitle());
            boolean insert = collectionService.insert(collection);
            if (insert){

            }

        }
        return result;
    }

    @ApiOperation("根据id删除收藏")
    @ApiImplicitParam(name = "id",required = true)
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public Result del(Integer id){
        Result result=new Result<>(true,StatusCode.Status.SUCCESS);
        if (null != id){
            collectionService.deleteById(id);
        }
        return result;
    }

}

