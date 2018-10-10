package com.zt.blog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel
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
    @ApiModelProperty(value = "标题")
    private String title;
    /**
     * 博客摘要
     */
    @ApiModelProperty(value = "博客摘要")
    private String digest;
    /**
     * 文章内容
     */
    @ApiModelProperty(value = "文章内容")
    private String content;
    /**
     * 分类id
     */
    @ApiModelProperty(value = "分类id")
    private Integer categoryId;
    /**
     * 标签（多个用","分隔）
     */
    @ApiModelProperty(value = "标签，多个用','分隔")
    private String tags;
    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间")
    private Date publishTime;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String userName;
    /**
     * 点击数
     */
    @ApiModelProperty(value = "点击数")
    private Integer clickHit;
    /**
     * 评论数
     */
    @ApiModelProperty(value = "评论数")
    private Integer replyHit;
    /**
     * 文章模式（1：公开，2：私有）
     */
    @ApiModelProperty(value = "文章模式（1：公开，2：私有）")
    private Integer showMode;
    /**
     * 状态（0：编辑中；1：已发布）
     */
    @ApiModelProperty(value = "状态（0：编辑中；1：已发布）")
    private Integer articleStatus;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 版本号
     */
    private Date version;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
