package com.ex.smp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("classpath:druid.properties")
public class DruidDataSourceConfig{
    @Value("${druid.driverClassName}")
    String driverClassName;
    @Value("${druid.url}")
    String url;
    @Value("${druid.username}")
    String username;
    @Value("${druid.password}")
    String password;
    @Value("${druid.initialSize}")
    Integer initialSize;
    @Value("${druid.maxActive}")
    Integer maxActive;
    @Value("${druid.maxWait}")
    Long maxWait;

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(Integer initialSize) {
        this.initialSize = initialSize;
    }

    public Integer getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(Integer maxActive) {
        this.maxActive = maxActive;
    }

    public Long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(Long maxWait) {
        this.maxWait = maxWait;
    }

    @Override
    public String toString() {
        return "DruidDataSourceConfig{" +
                "driverClassName='" + driverClassName + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", initialSize=" + initialSize +
                ", maxActive=" + maxActive +
                ", maxWait=" + maxWait +
                '}';
    }
}