package com.zt.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zt.blog.common.constant.StatusCode;
import com.zt.blog.common.entity.Result;
import com.zt.blog.model.Category;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 分类表 前端控制器
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-03
 */
@RestController
@RequestMapping("/blog/category")
@Api(description = "分类相关api")
public class CategoryController {


    @ApiOperation("获取所有的分类信息 ")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Result<List<Category>> list(){
        Result<List<Category>> result=new Result<>(true,StatusCode.Status.SUCCESS);
        Category category=new Category();
        List<Category> categoryList = category.selectList(new QueryWrapper<Category>().lambda()
                .eq(Category::getCategoryStatus, 1).orderByAsc(Category::getShowIndex));
        result.setData(categoryList);
        return result;
    }


}

