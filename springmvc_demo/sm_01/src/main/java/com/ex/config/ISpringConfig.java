package com.ex.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.ex.plugins.SQLExtractLog;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@ComponentScan(value = "com.ex" ,excludeFilters = {@ComponentScan.Filter({Controller.class})})
@PropertySource("classpath:druid.properties")
@EnableTransactionManagement
public class ISpringConfig {
    @Bean("druidDataSource2")
    public DataSource getDataSource(@Value("${druid.driverClassName}") String driverClassName, @Value("${druid.url}") String url,
                                    @Value("${druid.username}") String username, @Value("${druid.password}") String password){
        DruidDataSource d = new DruidDataSource();
        d.setDriverClassName(driverClassName);
        d.setUrl(url);
        d.setUsername(username);
        d.setPassword(password);
        return d;
    }
    @Bean("druidDataSource")
    public DataSource getDruidDataSource(DruidDataSourceConfig d){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(d.getDriverClassName());
        dataSource.setUrl(d.getUrl());
        dataSource.setUsername(d.getUsername());
        dataSource.setPassword(d.getPassword());
        dataSource.setMaxActive(d.getMaxActive());
        dataSource.setInitialSize(d.getInitialSize());
        dataSource.setMaxWait(d.getMaxWait());
        return dataSource;
    }
    @Bean("sqlSessionFactoryBean")
    public SqlSessionFactoryBean getSqlSessionFactoryBean(@Autowired DataSource druidDataSource) throws IOException {
        SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean() ;
        ssfb.setDataSource(druidDataSource);
        ssfb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*Mapper.xml"));
        ssfb.setTypeAliasesPackage("com.ex.pojo");
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setCacheEnabled(false);//关闭二级缓存
        ssfb.setPlugins(new SQLExtractLog());//插件
        ssfb.setConfiguration(configuration);
        return ssfb;
    }
    @Bean
    public MapperScannerConfigurer getMapperScannerConfigurer(){
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        msc.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        msc.setBasePackage("com.ex.mapper");
        return msc;
    }
    @Bean
    public TransactionManager getTransactionManager(@Qualifier("druidDataSource") @Autowired DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    @Bean("multipartResolver")
    public MultipartResolver createMultipartResolver(){
        CommonsMultipartResolver c = new CommonsMultipartResolver();
        c.setMaxUploadSize(1024*1024*5);
        c.setDefaultEncoding("UTF-8");//避免文件名乱码,但无法使内容不乱码
        return c;
    }
    @Bean
    public FastJsonHttpMessageConverter createFJHMConverter(){
        return new FastJsonHttpMessageConverter();
    }
}

