package com.zt.blog.common.property;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.sql.DataSource;


/**
 * @Author: zhoutian
 * @Description: 多数据源配置属性
 * @Date: 2018/7/10
 */
@Data
@ConfigurationProperties(prefix = "zt.datasource")
public class DataSourceProps {
    /**
     * 数据源类型
     */
    private Class<? extends DataSource> type;
    /**
     * jdbc驱动加载类
     */
    private String driverClassName;
    /**
     * *mapper.xml扫描路径
     */
    private String mapperLocations="classpath:mapping/*.xml";
    /**
     * dao层扫描路径
     */
    private String daoPackages;
    /**
     * entity 层扫描路径
     */
    private String entityPackages;
    private Integer initialSize=40;
    private Integer maxActive=100;
    private Integer minIdle=20;
    private Integer maxWait=120000;
    private boolean defaultAutoCommit=true;
    private boolean testOnBorrow=true;
    private boolean testWhileIdle=true;
    private Integer timeBetweenEvictionRunsMillis=90000;
    private String validationQuery="SELECT 1";
    private Integer validationQueryTimeout=2;
    private String url;
    private String username;
    private String password;
    private String txAopExpression;

}
