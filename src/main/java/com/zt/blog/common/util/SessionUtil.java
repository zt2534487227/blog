package com.zt.blog.common.util;

import com.zt.blog.common.constant.Constants;
import com.zt.blog.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * @Author: ZhouTian
 * @Description:
 * @Date: 2018/9/28
 */
public class SessionUtil {

    public static Subject getSubject(){
        return SecurityUtils.getSubject();
    }

    public static Session getSession(){
        return SecurityUtils.getSubject().getSession();
    }

    public static User getSessionUser(){
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        return user;
    }

    public static void setSessionUser(User user){
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(Constants.SESSION_USER,user);
    }

}
