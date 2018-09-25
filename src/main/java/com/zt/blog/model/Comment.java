package com.zt.blog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-25
 */
@TableName("t_comment")
@Data
public class Comment extends Model<Comment> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id，自增长
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 评论者昵称
     */
    private String userName;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 评论的内容
     */
    private String content;
    /**
     * 文章id
     */
    private Integer articleId;
    /**
     * 博主id
     */
    private Integer bloggerId;
    /**
     * 父级评论id
     */
    private Integer parentId;
    /**
     * 树形路径（如：0,1,2）
     */
    private String treePath;
    /**
     * 评论时间
     */
    private String commentTime;
    /**
     * 是否展示（1：是，2：否）
     */
    private Integer isShow;
    /**
     * 状态
     */
    private Integer commentStatus;
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
