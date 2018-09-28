package com.zt.blog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id，自增长
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户账号，唯一
     */
    private String userAccount;
    /**
     * 角色类型（管理员，普通用户）
     */
    private Integer roleType;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 密码  MD5(MD5(密码)+校验码)
     */
    private String password;
    /**
     * 校验方式(1：密码，2：手机验证码)
     */
    private Integer checkMode;
    /**
     * 校验码
     */
    private String checkCode;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 性别（1：男，2：女）
     */
    private Integer sex;
    /**
     * 用户头像
     */
    private String userImage;
    /**
     * 手机号
     */
    private String mobilePhone;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 个性签名
     */
    private String sign;
    /**
     * 个人简介
     */
    private String info;
    /**
     * 链接
     */
    private String url;
    /**
     * 地区id
     */
    private Integer areaId;
    /**
     * 地址
     */
    private String address;
    /**
     * 微信标识
     */
    private String wxId;
    /**
     * qq标识
     */
    private String qqId;
    /**
     * 新浪标识
     */
    private String sinaId;
    /**
     * 用户状态（1：正常，2：审核中，3：冻结中，4：已注销）
     */
    private Integer userStatus;
    /**
     * 状态说明
     */
    private String statusMemo;
    /**
     * 登录ip
     */
    private String loginIp;
    /**
     * 登录日期
     */
    private String loginTime;
    /**
     * 创建时间
     */
    private Date creatTime;
    /**
     * 版本号
     */
    private Date version;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
