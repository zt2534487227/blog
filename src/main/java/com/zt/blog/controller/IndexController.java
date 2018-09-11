package com.zt.blog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZhouTian
 * @Description:
 * @Date: 2018/9/3
 */
@RestController
@RequestMapping("/")
public class IndexController {

    @RequestMapping("/")
    public String index(){
        return "Hello,Welcome to zt Blog";
    }
}
