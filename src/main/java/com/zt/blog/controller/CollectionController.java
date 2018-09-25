package com.zt.blog.controller;


import com.zt.blog.common.constant.StatusCode;
import com.zt.blog.common.entity.Result;
import com.zt.blog.model.Article;
import com.zt.blog.model.Collection;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>
 * 我的收藏 前端控制器
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-03
 */
@Controller
@RequestMapping("/blog/collection")
@Api(description = "收藏相关api")
public class CollectionController {



    @ApiOperation("我的收藏列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Result getList(){
        Result<Collection> result=new Result<>(true,StatusCode.Status.SUCCESS);



        return result;
    }


    @ApiOperation("将文章id加入收藏")
    @ApiImplicitParam(name = "articleId",value = "文章id",required = true)
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(Integer articleId){
        Result result=new Result<>(StatusCode.Status.SUCCESS);
        if (null != articleId){
            Article article = new Article();
            article=article.selectById(articleId);
            Collection collection=new Collection();
            collection.setArticleId(articleId);
            collection.setUserId(1);
            collection.setArticleTitle(article.getTitle());

            boolean insert = collection.insert();
            result.setSuccess(insert);
        }
        return result;
    }

    @ApiOperation("根据id删除收藏")
    @ApiImplicitParam(name = "id",required = true)
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public Result del(Integer id){
        Result result=new Result<>(true,StatusCode.Status.SUCCESS);
        if (null != id){
            Collection collection=new Collection();
            collection.deleteById(id);
        }
        return result;
    }

}

