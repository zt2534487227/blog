package com.zt.blog.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-18
 */
@TableName("t_menu")
public class Menu implements Serializable {

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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(Integer showIndex) {
        this.showIndex = showIndex;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getTreePath() {
        return treePath;
    }

    public void setTreePath(String treePath) {
        this.treePath = treePath;
    }

    public Integer getMenuStatus() {
        return menuStatus;
    }

    public void setMenuStatus(Integer menuStatus) {
        this.menuStatus = menuStatus;
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
        return "Menu{" +
        ", id=" + id +
        ", menuName=" + menuName +
        ", showIndex=" + showIndex +
        ", parentId=" + parentId +
        ", treePath=" + treePath +
        ", menuStatus=" + menuStatus +
        ", createTime=" + createTime +
        ", version=" + version +
        "}";
    }
}
