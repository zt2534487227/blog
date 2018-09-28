package com.zt.blog.common.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * @Author: ZhouTian
 * @Description:
 * @Date: 2018/9/27
 */
public class AdminAuthorizingRealm extends AuthorizingRealm {


    @Resource
    private SessionDAO sessionDAO;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        authorizationInfo.addRole("admin");
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String useraccount = String.valueOf(token.getPrincipal());
        String password = new String((char[]) token.getCredentials());
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        Collection<Session> list = sessionDAO.getActiveSessions();
        System.out.println("在线人数：" + list.size());

        return new SimpleAuthenticationInfo(useraccount, password, getName());
    }
}
