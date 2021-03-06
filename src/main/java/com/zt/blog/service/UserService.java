package com.zt.blog.service;

import com.zt.blog.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-28
 */
public interface UserService extends IService<User> {

    User getByToken(String token);

    User getByAccount(String userAccount);

    User getByWxId(String wxId);

}
