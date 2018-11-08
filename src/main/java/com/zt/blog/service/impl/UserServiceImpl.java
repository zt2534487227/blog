package com.zt.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.blog.dao.UserDao;
import com.zt.blog.model.User;
import com.zt.blog.model.UserToken;
import com.zt.blog.service.UserService;
import com.zt.blog.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Autowired
    private UserTokenService userTokenService;

    @Override
    public User getByToken(String token) {
        if (StringUtils.isEmpty(token)){
            return null;
        }
        UserToken userToken = userTokenService.getByToken(token);
        if (null == userToken){
            return null;
        }
        return baseMapper.selectById(userToken.getUserId());
    }

    @Override
    public User getByAccount(String userAccount) {
        if (StringUtils.isEmpty(userAccount)){
            return null;
        }
        return baseMapper.selectOne(new LambdaQueryWrapper<>(new User()).eq(User::getUserAccount, userAccount));
    }

    @Override
    public User getByWxId(String wxId) {
        if (StringUtils.isEmpty(wxId)){
            return null;
        }
        return baseMapper.selectOne(new LambdaQueryWrapper<>(new User()).eq(User::getWxId, wxId));
    }
}
