package com.ex.cofig;

import java.util.*;

public class DatabaseConfig {
    //数据库配置
    private String driverClassName;
    private String url;
    private String username;
    private String password;



    public static DatabaseConfig buildDatabaseConfig(Properties p){
        Set<String> names = p.stringPropertyNames();
        DatabaseConfig databaseConfig = new DatabaseConfig();
        for (String name : names) {
            String value = p.getProperty(name);
            if(name.contains("driverClassName")){
                databaseConfig.driverClassName = value;
            }else if(name.contains("url")){
                databaseConfig.url = value;
            }else if(name.contains("username")){
                databaseConfig.username = value;
            }else if(name.contains("password")){
                databaseConfig.password = value;
            }
        }
        if(databaseConfig.driverClassName==null||databaseConfig.url==null || databaseConfig.username==null || databaseConfig.password==null){
            throw new RuntimeException("数据库配置未设置完毕:"+databaseConfig);
        }
        return databaseConfig;
    }



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

    @Override
    public String toString() {
        return "DatabaseConfig{" +
                "driverClassName='" + driverClassName + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
