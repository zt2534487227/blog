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
 * 分类表
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_category")
public class Category extends Model<Category> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id，自增长
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 分类名称
     */
    private String cName;
    /**
     * 排序
     */
    private Integer showIndex;
    /**
     * 状态（1：有效，2：无效）
     */
    private Integer categoryStatus;
    /**
     * 创建时间
     */
    private Date creatTime;
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
