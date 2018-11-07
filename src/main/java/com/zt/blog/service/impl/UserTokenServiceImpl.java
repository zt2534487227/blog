package com.zt.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.blog.common.util.VerifyCodeUtil;
import com.zt.blog.dao.UserTokenDao;
import com.zt.blog.model.UserToken;
import com.zt.blog.service.UserTokenService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ZhouTian
 * @since 2018-11-06
 */
@Service
public class UserTokenServiceImpl extends ServiceImpl<UserTokenDao, UserToken> implements UserTokenService {


    @Override
    public UserToken generateToken(Integer userId) {
        if (null == userId){
            return null;
        }
        UserToken userToken = baseMapper.selectOne(new LambdaQueryWrapper<>(new UserToken()).eq(UserToken::getUserId, userId));
        if (null == userToken){
            userToken=new UserToken();
            userToken.setUserId(userId);
            userToken.setCreateTime(new Date());
            String token=System.currentTimeMillis()+VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_ALL_MIXED,5,null);
            userToken.setToken(token);
            userToken.setExpireTime(DateUtils.addDays(new Date(),7));
        }else {
            userToken.setExpireTime(DateUtils.addDays(new Date(),7));
        }
        saveOrUpdate(userToken);
        return userToken;
    }

    @Override
    public UserToken getByToken(String token) {
        if (StringUtils.isEmpty(token)){
            return null;
        }
        return baseMapper.selectOne(new LambdaQueryWrapper<>(new UserToken())
                .eq(UserToken::getToken, token));
    }
}
