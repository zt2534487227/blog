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
 * 文章表
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_article")
public class Article extends Model<Article> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id，自增长
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 博客摘要
     */
    private String digest;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 分类id
     */
    private Integer categoryId;
    /**
     * 标签（多个用","分隔）
     */
    private String tags;
    /**
     * 发布时间
     */
    private Date publishTime;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 点击数
     */
    private Integer ckickHit;
    /**
     * 评论数
     */
    private Integer replyHit;
    /**
     * 文章模式（1：公开，2：私有）
     */
    private Integer showMode;
    /**
     * 状态（0：编辑中；1：已发布）
     */
    private Integer articleStatus;
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
