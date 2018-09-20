package com.zt.blog.common.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Author: ZhouTian
 * @Description: 启动加载类，会在所有的javabean初始化之后执行，
 * 可以用来执行一些初始化数据
 * @Date: 2018/9/14
 */
@Component
public class ApplicationConfig implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        //System.out.println("Java Beans 初始化结束，开始加载数据.....");
        //比如 加载一些字典参数等 一些常用缓存  启动时候讲数据将博客数据放在es中

    }
}
