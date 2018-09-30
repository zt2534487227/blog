package com.zt.blog.controller;

import com.zt.blog.common.constant.BaseConstants;
import com.zt.blog.common.constant.StatusCode;
import com.zt.blog.common.entity.Result;
import com.zt.blog.common.util.Md5Encrypt;
import com.zt.blog.common.util.VerifyCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Author: ZhouTian
 * @Description:
 * @Date: 2018/9/28
 */
@RestController
@RequestMapping("/")
@Api(description = "验证码 api")
public class VerifyCodeController {

    private static final Logger log=LoggerFactory.getLogger(VerifyCodeController.class);

    /**
     * 获取验证码图片和文本(验证码文本会保存在HttpSession中)
     */
    @ApiOperation("获取图片验证码")
    @RequestMapping(value = "/getVerifyCodeImage",method = RequestMethod.GET)
    public void getVerifyCodeImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 设置页面不缓存
        response.setHeader("Parama", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        String verifyCode = VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_ALL_MIXED, 4, null);
        // 将验证码加密放到HttpSession里面
        request.getSession().setAttribute(BaseConstants.SESSION_VERIFYCODE, Md5Encrypt.md5(verifyCode.toLowerCase()));
        log.info("本次生成的验证码为[" + verifyCode + "],已存放到HttpSession中");
        // 设置输出的内容的类型为JPEG图像
        response.setContentType("image/jpeg");
        BufferedImage bufferedImage = VerifyCodeUtil.generateImageCode(verifyCode, 90, 30, 10, true, Color.WHITE,
                Color.BLACK, null);
        // 写给浏览器
        ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());
    }

    @RequestMapping(value="/checkVerifyCode",method=RequestMethod.POST)
    private Result<Boolean> checkVerifyCode(String verifyCode, HttpServletRequest request) {
        Result<Boolean> result=new Result<>(true,StatusCode.Status.SUCCESS);
        result.setData(false);
        if (!StringUtils.isEmpty(verifyCode)) {
            HttpSession session = request.getSession();
            String sessioncode = (String) session.getAttribute("verifyCode");
            session.removeAttribute(BaseConstants.SESSION_VERIFYCODE);
            // 加密后验证
            String sha1hexverifycode = Md5Encrypt.md5(verifyCode.toLowerCase());
            if (sha1hexverifycode.equalsIgnoreCase(sessioncode)) {
                result.setData(true);
                return result;
            }
        }
        return result;
    }

    @ApiOperation("获取验证码")
    @RequestMapping(value = "/getVerifyCode",method = RequestMethod.GET)
    public Result<String> getVerifyCode(HttpServletRequest request) {
        Result<String> result=new Result<>(true,StatusCode.Status.SUCCESS);
        // 生成验证码
        String verifyCode = VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_ALL_MIXED, 4, null);
        // 将验证码加密放到HttpSession里面
        request.getSession().setAttribute(BaseConstants.SESSION_VERIFYCODE, Md5Encrypt.md5(verifyCode.toLowerCase()));
        log.info("本次生成的验证码为[" + verifyCode + "],已存放到HttpSession中");
        result.setData(verifyCode);
        return result;
    }



}
