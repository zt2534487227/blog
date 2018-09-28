package com.zt.blog.service.impl;

import com.zt.blog.model.User;
import com.zt.blog.dao.UserDao;
import com.zt.blog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}
