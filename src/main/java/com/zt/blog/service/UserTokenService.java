package com.zt.blog.service;

import com.zt.blog.model.UserToken;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ZhouTian
 * @since 2018-11-06
 */
public interface UserTokenService extends IService<UserToken> {

    UserToken generateToken(Integer userId);

    UserToken getByToken(String token);

}
