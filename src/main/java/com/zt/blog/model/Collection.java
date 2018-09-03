package com.zt.blog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 我的收藏
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-03
 */
@TableName("t_collection")
public class Collection implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id，自增长
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id，user表id
     */
    private Integer userId;
    /**
     * 文章标题
     */
    private String articleTitle;
    /**
     * 文章id
     */
    private String articleId;
    /**
     * 收藏日期
     */
    private Date createTime;
    /**
     * 版本号
     */
    private Date version;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Collection{" +
        ", id=" + id +
        ", userId=" + userId +
        ", articleTitle=" + articleTitle +
        ", articleId=" + articleId +
        ", createTime=" + createTime +
        ", version=" + version +
        "}";
    }
}
