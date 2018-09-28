package com.zt.blog.model;

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
 * @since 2018-09-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_menu")
public class Menu extends Model<Menu> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 排序
     */
    private Integer showIndex;
    private Integer parentId;
    private String treePath;
    /**
     * 菜单状态（0：停用，1：启用）
     */
    private Integer menuStatus;
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
