package com.zt.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zt.blog.common.constant.StatusCode;
import com.zt.blog.common.entity.Result;
import com.zt.blog.model.Menu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: ZhouTian
 * @Description:
 * @Date: 2018/9/18
 */
@RestController
@RequestMapping("/blog/menu")
@Api(description = "菜单相关api")
public class MenuController {

    @ApiOperation("获取首页菜单列表")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Result<List<Menu>> getList(){
        Result<List<Menu>> result=new Result<>(true,StatusCode.Status.SUCCESS);
        Menu menu=new Menu();
        List<Menu> menus = menu.selectList(new QueryWrapper<Menu>().lambda()
                .eq(Menu::getMenuStatus, 1).orderByAsc(Menu::getShowIndex));
        result.setData(menus);
        return result;
    }




}
