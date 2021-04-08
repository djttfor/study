package com.ex.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Controller;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@ComponentScan(value = "com.ex" ,excludeFilters = {@ComponentScan.Filter({Controller.class})})
@EnableAspectJAutoProxy
@MapperScan("com.ex.mapper")
public class ISpringConfig {

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
    @Bean
    public SqlSessionFactoryBean getSqlSessionFactoryBean(DataSource dataSource) throws IOException {
        SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
        ssfb.setDataSource(dataSource);
        ssfb.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/*Mapper.xml"));
        ssfb.setTypeAliasesPackage("com.ex.entity");
        return ssfb;
    }
//    @Bean
//    public MapperScannerConfigurer getMapperScannerConfigurer(){
//        MapperScannerConfigurer msc = new MapperScannerConfigurer();
//        msc.setBasePackage("com.ex.mapper");
//        return msc;
//    }
}
