package com.zt.blog.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-25
 */
@TableName("t_attach")
@Data
public class Attach extends Model<Attach> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，uuid
     */
    private String id;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件大小
     */
    private BigDecimal fileSize;
    /**
     * 文件类型
     */
    private String fileType;
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
