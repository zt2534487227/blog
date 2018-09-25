package com.zt.blog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 我的收藏
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-25
 */
@TableName("t_collection")
@Alias("t_collection")
@Data
public class Collection extends Model<Collection> {

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
    private Integer articleId;
    /**
     * 收藏日期
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
