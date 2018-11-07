package com.zt.blog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * 
 * </p>
 *
 * @author ZhouTian
 * @since 2018-11-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_token")
public class UserToken extends Model<UserToken> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @TableField("userId")
    private Integer userId;

    /**
     * token
     */
    private String token;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private Date createTime;

    /**
     * 失效时间
     */
    @TableField("expireTime")
    private Date expireTime;


    private transient String userName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
