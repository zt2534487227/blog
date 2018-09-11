package com.zt.blog.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-11
 */
@TableName("t_attach")
public class Attach implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String fileName;
    private String filePath;
    private BigDecimal fileSize;
    private String fileType;
    private Date createTime;
    private Date version;


    public Attach() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public BigDecimal getFileSize() {
        return fileSize;
    }

    public void setFileSize(BigDecimal fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
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
        return "Attach{" +
        ", id=" + id +
        ", fileName=" + fileName +
        ", filePath=" + filePath +
        ", fileSize=" + fileSize +
        ", fileType=" + fileType +
        ", createTime=" + createTime +
        ", version=" + version +
        "}";
    }
}
