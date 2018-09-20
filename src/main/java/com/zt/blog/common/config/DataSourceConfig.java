package com.zt.blog.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.google.common.collect.Lists;
import com.zt.blog.common.property.DataSourceProps;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.*;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ZhouTian
 * @Description:
 * @Date: 2018/9/3
 */
@Configuration
@EnableConfigurationProperties(DataSourceProps.class)
@EnableTransactionManagement
public class DataSourceConfig implements EnvironmentAware {

    private DataSourceProps dataSourceProps;

    public void setEnvironment(Environment environment) {
        if (this.dataSourceProps ==null){
            dataSourceProps= Binder.get(environment).bind(DataSourceProps.class.getAnnotation(ConfigurationProperties.class).prefix()
                    , DataSourceProps.class).get();
        }
    }

    @Bean
    public DataSource dataSource(){
        DruidDataSource dataSource=new DruidDataSource();
        dataSource.setDriverClassName(dataSourceProps.getDriverClassName());
        dataSource.setUrl(dataSourceProps.getUrl());
        dataSource.setUsername(dataSourceProps.getUsername());
        dataSource.setPassword(dataSourceProps.getPassword());
        dataSource.setInitialSize(dataSourceProps.getInitialSize());
        dataSource.setMaxActive(dataSourceProps.getMaxActive());
        dataSource.setMinIdle(dataSourceProps.getMinIdle());
        dataSource.setDefaultAutoCommit(dataSourceProps.isDefaultAutoCommit());
        dataSource.setTestOnBorrow(dataSourceProps.isTestOnBorrow());
        dataSource.setValidationQuery(dataSourceProps.getValidationQuery());
        return  dataSource;
    }


    @Bean("mybatisSqlSession")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean=new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        sqlSessionFactoryBean.setTypeAliasesPackage(dataSourceProps.getEntityPackages());
        List<Interceptor> list=Lists.newArrayList();
        list.add(new PaginationInterceptor());//分页插件
        list.add(new OptimisticLockerInterceptor());//乐观锁插件
        list.add(new PerformanceInterceptor());//性能分析插件 输出sql语句和执行时间
        sqlSessionFactoryBean.setPlugins(list.toArray(new Interceptor[]{}));
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        sqlSessionFactoryBean.setConfiguration(configuration);
        sqlSessionFactoryBean.setGlobalConfig(new GlobalConfig()
                //.setSqlInjector(new LogicSqlInjector())   //sql注入器
                .setDbConfig(new GlobalConfig.DbConfig()
                        //.setTablePrefix("t_")  //表前缀
                        //.setLogicDeleteValue("-1")  //逻辑删除
                        //.setLogicNotDeleteValue("0")
                        //.setIdType(IdType.AUTO) //主键策略
                        .setCapitalMode(false) //大写下划线转换
                        .setColumnUnderline(false)  //驼峰下划线转换
                        .setFieldStrategy(FieldStrategy.NOT_EMPTY)
                ));
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(dataSourceProps.getMapperLocations()));
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 事务管理
     * @return
     */
    @Bean
    public DataSourceTransactionManager TransactionManager(){
        DataSourceTransactionManager transactionManager=new DataSourceTransactionManager(dataSource());
        return transactionManager;
    }

    /**
     * 声明式事务
     * @return
     */
    @Bean
    public TransactionInterceptor txAdvice(){
        NameMatchTransactionAttributeSource source=new NameMatchTransactionAttributeSource();
        //只读事务
        RuleBasedTransactionAttribute readOnlyTx=new RuleBasedTransactionAttribute();
        readOnlyTx.setReadOnly(true);
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED );
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        Map<String, TransactionAttribute> txMap = new HashMap<String, TransactionAttribute>();
        txMap.put("add*", requiredTx);
        txMap.put("create*", requiredTx);
        txMap.put("insert*", requiredTx);
        txMap.put("save*", requiredTx);
        txMap.put("update*", requiredTx);
        txMap.put("modify*", requiredTx);
        txMap.put("edit*", requiredTx);
        txMap.put("batch*", requiredTx);
        txMap.put("remove*", requiredTx);
        txMap.put("delete*", requiredTx);
        txMap.put("get*", readOnlyTx);
        txMap.put("find*", readOnlyTx);
        txMap.put("search*", readOnlyTx);
        txMap.put("select*", readOnlyTx);
        txMap.put("query*", readOnlyTx);
        txMap.put("list*", readOnlyTx);
        txMap.put("count*", readOnlyTx);
        source.setNameMap( txMap );
        return new TransactionInterceptor(TransactionManager(),source);
    }

    /**
     * 事务aop配置
     * @return
     */
    @Bean
    public Advisor txAdvisor(){
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(dataSourceProps.getTxAopExpression());
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }

}
