package com.zt.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.zt.blog.common.constant.Constants;
import com.zt.blog.common.entity.Result;
import com.zt.blog.common.property.AppletProps;
import com.zt.blog.common.util.DateUtil;
import com.zt.blog.common.util.HttpClientUtil;
import com.zt.blog.common.util.Md5Encrypt;
import com.zt.blog.model.*;
import com.zt.blog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * @Author: ZhouTian
 * @Description: 对外开放接口
 * @Date: 2018/11/6
 */
@RestController
@RequestMapping("/api")
public class OpenController {

    @Autowired
    private UserTokenService userTokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private ConcernService concernService;
    @Autowired
    private AppletProps appletProps;

    /**
     * 登录
     * @param request
     * @return
     */
    @RequestMapping("/login")
    public Result login(HttpServletRequest request){
        String wxId = request.getParameter("wxId");
        String userAccount = request.getParameter("userAccount");
        String password = request.getParameter("password");
        if (StringUtils.isEmpty(userAccount)||StringUtils.isEmpty(password)){
            return new Result(Constants.Status.PARAM_EMPTY);
        }
        User user = userService.getOne(new LambdaQueryWrapper<>(new User()).eq(User::getUserAccount, userAccount));
        if (null == user){
            return new Result(Constants.Status.USER_NOT_EXISTS);
        }
        String md5 = Md5Encrypt.md5(password + user.getCheckCode());
        if (!md5.equals(user.getPassword())){
           return new Result<User>(Constants.Status.USER_LOGIN_ERROR);
        }
        UserToken userToken = userTokenService.generateToken(user.getId());
        Result<UserToken> result=new Result<>(true,Constants.Status.SUCCESS);
        userToken.setUserName(user.getNickName());
        result.setData(userToken);
        return result;
    }

    /**
     * 获取微信session_key和openid
     * @param request
     * @return
     */
    @RequestMapping("/getWxSession")
    public Result getWxSession(HttpServletRequest request){
        String code = request.getParameter("code");
        if (StringUtils.isEmpty(code)){
            return new Result(Constants.Status.PARAM_EMPTY);
        }
        Map<String,Object> params=Maps.newHashMap();
        params.put("appid", appletProps.getAppId());
        params.put("secret", appletProps.getSecret());
        params.put("js_code", code);
        params.put("grant_type", appletProps.getGrantType());
        //http请求 获取微信的session_key和openId
        String url = appletProps.getUrl();
        String res = HttpClientUtil.httpPost(url, params);
        JSONObject jsonObject=(JSONObject) JSONObject.parse(res);
        System.out.println(jsonObject);
        String openid= jsonObject.getString("openid");
        Result<UserToken> result=new Result<>(true,Constants.Status.SUCCESS);
        User user = userService.getOne(new LambdaQueryWrapper<>(new User()).eq(User::getWxId, openid));
        if (null != user){
            UserToken userToken = userTokenService.generateToken(user.getId());
            userToken.setUserName(user.getNickName());
            result.setData(userToken);
        }
        return result;
    }

    /**
     * 校验是否登录
     * @param request
     * @return
     */
    @RequestMapping("/checkLogin")
    public Result checkLogin(HttpServletRequest request){
        String token = request.getParameter("token");
        if (StringUtils.isEmpty(token)){
            return new Result(Constants.Status.TOKEN_EXPIRED);
        }
        UserToken userToken = userTokenService.getByToken(token);
        if (null == userToken){
            return new Result(Constants.Status.TOKEN_NOT_EXISTS);
        }
        Date expireTime = userToken.getExpireTime();
        if (DateUtil.before(expireTime,new Date())){//token过期
            return new Result(Constants.Status.TOKEN_EXPIRED);
        }
        return new Result(true,Constants.Status.SUCCESS);
    }

    /**
     * 登出
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    public Result logout(HttpServletRequest request){
        String token = request.getParameter("token");
        UserToken userToken = userTokenService.getByToken(token);
        if (null != userToken){
            userToken.setExpireTime(new Date());
            userTokenService.updateById(userToken);
        }
        return new Result(true,Constants.Status.SUCCESS);
    }

    /**
     * 修改用户昵称
     * @param request
     * @return
     */
    @RequestMapping("/user/modifyUserName")
    public Result modifyUserName(HttpServletRequest request){
        String token = request.getParameter("token");
        String userName = request.getParameter("userName");
        User user = userService.getByToken(token);
        if (null == user){
            return new Result(Constants.Status.USER_NOT_LOGIN);
        }
        if (StringUtils.isEmpty(userName)){
            return new Result(Constants.Status.PARAM_EMPTY);
        }
        if (userName.equals(user.getNickName())){
            return new Result(Constants.Status.REQUEST_ERROR.getCode(),"两次昵称相同");
        }
        user.setNickName(userName);
        userService.updateById(user);
        return new Result(true,Constants.Status.SUCCESS);
    }


    /**
     * 我的博客列表
     * @param request
     * @return
     */
    @RequestMapping("/user/articleList")
    public Result articleList(HttpServletRequest request){
        String token = request.getParameter("token");
        User user = userService.getByToken(token);
        if (null == user){
            return new Result(Constants.Status.USER_NOT_LOGIN);
        }
        int pageSize = getParamInt(request, "pageSize", 10);
        int pageNo = getParamInt(request, "pageNo", 1);
        IPage<Article> page=new Page<>(pageNo,pageSize);
        page=articleService.page(page,new LambdaQueryWrapper<>(new Article())
            .eq(Article::getUserId,user.getId()));
        Result<IPage<Article>> result=new Result<>(true,Constants.Status.SUCCESS);
        result.setData(page);
        return result;
    }


    /**
     * 我的收藏列表
     * @param request
     * @return
     */
    @RequestMapping("/user/collectionList")
    public Result collectionList(HttpServletRequest request){
        String token = request.getParameter("token");
        User user = userService.getByToken(token);
        if (null == user){
            return new Result(Constants.Status.USER_NOT_LOGIN);
        }
        int pageSize = getParamInt(request, "pageSize", 10);
        int pageNo = getParamInt(request, "pageNo", 1);
        IPage<Collection> page=new Page<>(pageNo,pageSize);
        page=collectionService.page(page,new LambdaQueryWrapper<>(new Collection())
                .eq(Collection::getUserId,user.getId()));
        Result<IPage<Collection>> result=new Result<>(true,Constants.Status.SUCCESS);
        result.setData(page);
        return result;
    }

    /**
     * 我的评论列表
     * @param request
     * @return
     */
    @RequestMapping("/user/commentList")
    public Result commentList(HttpServletRequest request){
        String token = request.getParameter("token");
        User user = userService.getByToken(token);
        if (null == user){
            return new Result(Constants.Status.USER_NOT_LOGIN);
        }
        int pageSize = getParamInt(request, "pageSize", 10);
        int pageNo = getParamInt(request, "pageNo", 1);
        IPage<Comment> page=new Page<>(pageNo,pageSize);
        commentService.page(page,new LambdaQueryWrapper<>(new Comment())
            .eq(Comment::getUserId,user.getId()));
        Result<IPage<Comment>> result=new Result<>(true,Constants.Status.SUCCESS);
        result.setData(page);
        return result;
    }

    /**
     * 我的关注列表
     * @param request
     * @return
     */
    @RequestMapping("/user/concernList")
    public Result concernList(HttpServletRequest request){
        String token = request.getParameter("token");
        User user = userService.getByToken(token);
        if (null == user){
            return new Result(Constants.Status.USER_NOT_LOGIN);
        }
        int pageSize = getParamInt(request, "pageSize", 10);
        int pageNo = getParamInt(request, "pageNo", 1);
        IPage<Concern> page=new Page<>(pageNo,pageSize);
        concernService.page(page,new LambdaQueryWrapper<>(new Concern())
            .eq(Concern::getUserId,user.getId()));
        Result<IPage<Concern>> result=new Result<>(true,Constants.Status.SUCCESS);
        result.setData(page);
        return result;
    }

    /**
     * 搜索列表
     * @param request
     * @return
     */
    @RequestMapping("/search")
    public Result search(HttpServletRequest request){
        int pageSize = getParamInt(request, "pageSize", 10);
        int pageNo = getParamInt(request, "pageNo", 1);
        IPage<Article> page=new Page<>(pageNo,pageSize);
        articleService.page(page, new LambdaQueryWrapper<>(new Article())
                .eq(Article::getShowMode, 1) //公开
                .eq(Article::getArticleStatus, 1)//已发布
                .orderByDesc(Article::getPublishTime));
        Result<IPage<Article>> result=new Result<>(true,Constants.Status.SUCCESS);
        result.setData(page);
        return result;
    }


    /**
     * 文章详情
     * @param request
     * @return
     */
    @RequestMapping("/articleDetail")
    public Result articleDetail(HttpServletRequest request){
        String id = request.getParameter("id");
        if (StringUtils.isEmpty(id)){
            return new Result(Constants.Status.PARAM_EMPTY);
        }
        Article article = articleService.getById(Integer.parseInt(id));
        Result<Article> result=new Result<>(true,Constants.Status.SUCCESS);
        result.setData(article);
        return result;
    }


    private int getParamInt(HttpServletRequest request,String param,int deValue){
        try {
            String parameter = request.getParameter(param);
            if (StringUtils.isEmpty(parameter)){
                return Integer.parseInt(parameter);
            }
            return deValue;
        }catch (Exception e){
            return deValue;
        }
    }

}
