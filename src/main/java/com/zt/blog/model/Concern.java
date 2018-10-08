package com.zt.blog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 我的关注
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_concern")
public class Concern extends Model<Concern> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id，自增长
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 关注的用户昵称
     */
    private String concernName;
    /**
     * 关注的用户id
     */
    private Integer concernId;
    /**
     * 关注时间
     */
    private Date createTime;
    /**
     * 版本号
     */
    @Version
    private Date version;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
