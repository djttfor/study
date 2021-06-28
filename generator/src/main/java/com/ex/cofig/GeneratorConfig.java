package com.ex.cofig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class GeneratorConfig {
    //项目根路径
    private String rootPath;
    //包名
    private String packageName;
    //数据库配置
    private DatabaseConfig databaseConfig;

    //entity类是否需要实现Serializable接口
    private boolean serializableFlag = false;

    //作者
    private String author;

    //生成器模式
    private GeneratorMode mode;

    //是否生成Controller
    private boolean requireController ;

    private List<String> tables;//选择要生成的表
    private List<String> excludeTables;//排除的表

    public GeneratorConfig(){
        //默认
        mode = GeneratorMode.MYBATIS_PLUS;
        //默认生成controller
        requireController = true;
    }

    public void buildDatabaseConfig(InputStream in) throws IOException {
        Properties p = new Properties();
        p.load(in);
        this.databaseConfig = DatabaseConfig.buildDatabaseConfig(p);
    }

    public void selectTable(String...tables){
        Objects.requireNonNull(tables);
        if(tables.length>0){
            this.tables = Arrays.asList(tables);
            this.excludeTables = null;
        }
    }

    public List<String> getSelectTables() {
        return tables;
    }

    public void setExcludeTables(String...tables){
        Objects.requireNonNull(tables);
        if(tables.length>0){
            this.excludeTables = Arrays.asList(tables);
            this.tables = null;
        }
    }

    public List<String> getExcludeTables() {
        return excludeTables;
    }


    public boolean isRequireController() {
        return requireController;
    }

    public void setRequireController(boolean requireController) {
        this.requireController = requireController;
    }

    public GeneratorMode getMode() {
        return mode;
    }

    public void setMode(GeneratorMode mode) {
        this.mode = mode;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isSerializableFlag() {
        return serializableFlag;
    }
    public void setSerializableFlag(boolean serializableFlag) {
        this.serializableFlag = serializableFlag;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public DatabaseConfig getDatabaseConfig() {
        return databaseConfig;
    }

    public void setDatabaseConfig(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

}
