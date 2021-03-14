package com.ex.generator;

import com.ex.cofig.*;
import com.ex.database.entity.Column;
import com.ex.database.entity.Table;
import com.ex.entity.Field;
import com.ex.util.DatabaseUtil;
import com.ex.util.NameUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class CodeGenerator {
    private GeneratorConfig generatorConfig;
    public CodeGenerator(){}
    public void setGeneratorConfig(GeneratorConfig generatorConfig) {
        this.generatorConfig = generatorConfig;
    }
    public CodeGenerator(GeneratorConfig generatorConfig) {
        this.generatorConfig = generatorConfig;
    }
    private static final Logger log = LoggerFactory.getLogger(CodeGenerator.class);
    public static void main(String[] args) throws Exception {
        //测试代码
//        GeneratorConfig config = new GeneratorConfig();
//        config.setRootPath("F:\\Java\\generator\\src\\main\\java");
//        config.setPackageName("com.ex.test.com.ex.ssm");
//        InputStream in = CodeGenerator.class.getClassLoader().getResourceAsStream("druid.properties");
//        config.buildDatabaseConfig(in);
//        config.selectTable("user","account");
//        CodeGenerator codeGenerator = new CodeGenerator(config);
//        codeGenerator.generate();

    }
    //执行生成
     public void generate() throws Exception {
         DatabaseConfig databaseConfig = generatorConfig.getDatabaseConfig();
         List<String> selectTables = generatorConfig.getSelectTables();
         List<String> excludeTables = generatorConfig.getExcludeTables();
         List<Table> tables;
         if (selectTables==null && excludeTables ==null){
             tables = DatabaseUtil.getDatabaseConfig(databaseConfig.getDriverClassName(),
                     databaseConfig.getUrl(), databaseConfig.getUsername(), databaseConfig.getPassword());
         }else if(selectTables == null){
             tables = DatabaseUtil.getDatabaseConfig(databaseConfig.getDriverClassName(),
                     databaseConfig.getUrl(), databaseConfig.getUsername(), databaseConfig.getPassword(),null,excludeTables);
         }else if(excludeTables==null){
             tables = DatabaseUtil.getDatabaseConfig(databaseConfig.getDriverClassName(),
                     databaseConfig.getUrl(), databaseConfig.getUsername(), databaseConfig.getPassword(),selectTables,null);
         }else{
             throw new RuntimeException("表配置出错了，请检查");
         }

        UrlConfig urlConfig = new UrlConfig(generatorConfig.getRootPath(),generatorConfig.getPackageName(),
                tables,generatorConfig.isRequireController());
        urlConfig.execute();//生成所有文件

        Configuration configuration = new Configuration(new Version("2.3.30"));
        configuration.setClassForTemplateLoading(CodeGenerator.class,"/");
        log.info("========================开始生成==========================");
        generateEntity(configuration,urlConfig,tables);//生成entity
        generateMapper(configuration,urlConfig,tables);
        generateService(configuration,urlConfig,tables);
        generateServiceImpl(configuration,urlConfig,tables);
        if(generatorConfig.isRequireController()){
            generateControllerImpl(configuration,urlConfig,tables);
        }
        log.info("========================生成完毕==========================");
    }

    //配置entity.java
    private FillConfig buildEntityData(UrlConfig urlConfig, List<Table> tables){
        Map<String,Map<String,Object>> entityData = new HashMap<>();
        List<String> classNames = new ArrayList<>();
        Map<String, String> filePath = urlConfig.getFilePath();
        for (Table table : tables) {
            //填充entity所需的配置
            Map<String,Object> entry = new HashMap<>();
            List<Field> fields = new ArrayList<>();
            List<String> packages = new ArrayList<>();
            List<Column> columnList = table.getColumnList();
            String className = NameUtil.buildClassName(table.getTableName());
            for (Column column : columnList) {
                //java 类型(全限定类名)
                String javaType = column.getJavaType();
                int lastIndex = javaType.lastIndexOf('.');
                //java 类型名称
                String javaTypeName = javaType.substring(lastIndex + 1);
                //java 成员变量名称
                String javaVariableName = NameUtil.buildFieldName(column.getName());
                String packageName = javaType.substring(0, lastIndex);
                if (!"java.lang".equalsIgnoreCase(packageName)) {
                    packages.add(javaType);
                }
                //主键注解@TableId,不考虑联合主键
                if (column.isPrimary() && column.isAutoIncrement()) {
                    entry.put("autoIncrement",true);
                    entry.put("id",javaVariableName);
                }
                fields.add(new Field(javaTypeName,javaVariableName));
            }
            //是否需要实现Serializable接口
            if(generatorConfig.isSerializableFlag()){
                entry.put("serializableFlag",true);
                entry.put("serializablePackageName",Serializable.class.getName());
            }else{
                entry.put("serializableFlag",false);
            }
            //作者
            if(null==generatorConfig.getAuthor()){
                entry.put("author","ttfor");
            }else{
                entry.put("author",generatorConfig.getAuthor());
            }
            //生成日期
            entry.put("date",new Date());

            //实体类基础属性
            entry.put("fields",fields);
            entry.put("packages",packages);
            entry.put("className",className);
            entry.put("packageName",urlConfig.getPackageName()+".entity");
            classNames.add(className);
            entityData.put(filePath.get(className),entry);
        }
        return new FillConfig(classNames,entityData);
    }
    //配置mapper.java
    private FillConfig buildMapperData(UrlConfig urlConfig, List<Table> tables){
        Map<String,Map<String,Object>> entityData = new HashMap<>();
        List<String> classNames = new ArrayList<>();
        Map<String, String> filePath = urlConfig.getFilePath();
        for (Table table : tables) {
            //填充entity所需的配置
            Map<String,Object> entry = new HashMap<>();
            String entityName = NameUtil.buildClassName(table.getTableName());
            String mapperName = entityName + "Mapper";
            String packageName = urlConfig.getPackageName();

            entry.put("packageName",packageName+".mapper");
            entry.put("mapperName",mapperName);
            //是否为mp模式
            if(GeneratorMode.MYBATIS_PLUS.equals(generatorConfig.getMode())){
                entry.put("mybatisPlusFlag",true);
                entry.put("entityPackageName",packageName+".entity."+entityName);
                entry.put("entityName",entityName);
            }else{
                entry.put("mybatisPlusFlag",false);
            }

            //作者
            if(null==generatorConfig.getAuthor()){
                entry.put("author","ttfor");
            }else{
                entry.put("author",generatorConfig.getAuthor());
            }
            //生成日期
            entry.put("date",new Date());
            classNames.add(mapperName);
            entityData.put(filePath.get(mapperName),entry);
        }
        return new FillConfig(classNames,entityData);
    }
    //配置service.java
    private FillConfig buildServiceData(UrlConfig urlConfig, List<Table> tables){
        Map<String,Map<String,Object>> entityData = new HashMap<>();
        List<String> classNames = new ArrayList<>();
        Map<String, String> filePath = urlConfig.getFilePath();
        for (Table table : tables) {
            //填充entity所需的配置
            Map<String,Object> entry = new HashMap<>();
            String entityName = NameUtil.buildClassName(table.getTableName());
            String serviceName = entityName + "Service";
            String packageName = urlConfig.getPackageName();

            entry.put("packageName",packageName+".service");
            entry.put("serviceName",serviceName);
            //是否为mp模式
            if(GeneratorMode.MYBATIS_PLUS.equals(generatorConfig.getMode())){
                entry.put("mybatisPlusFlag",true);
                entry.put("entityPackageName",packageName+".entity."+entityName);
                entry.put("entityName",entityName);
            }else{
                entry.put("mybatisPlusFlag",false);
            }

            //作者
            if(null==generatorConfig.getAuthor()){
                entry.put("author","ttfor");
            }else{
                entry.put("author",generatorConfig.getAuthor());
            }
            //生成日期
            entry.put("date",new Date());
            classNames.add(serviceName);
            entityData.put(filePath.get(serviceName),entry);
        }
        return new FillConfig(classNames,entityData);
    }
    //配置serviceImpl.java
    private FillConfig buildServiceImplData(UrlConfig urlConfig, List<Table> tables){
        Map<String,Map<String,Object>> entityData = new HashMap<>();
        List<String> classNames = new ArrayList<>();
        Map<String, String> filePath = urlConfig.getFilePath();
        for (Table table : tables) {
            //填充entity所需的配置
            Map<String,Object> entry = new HashMap<>();
            String entityName = NameUtil.buildClassName(table.getTableName());
            String serviceName = entityName + "Service";
            String mapperName = entityName+"Mapper";
            String serviceImplName = serviceName+"Impl";

            String packageName = urlConfig.getPackageName();
            String entityPackageName = packageName+".entity."+entityName;
            String mapperPackageName = packageName+".mapper."+mapperName;
            String servicePackageName = packageName+".service."+serviceName;

            entry.put("packageName",packageName+".service.impl");
            entry.put("serviceName",serviceName);
            entry.put("entityName",entityName);
            entry.put("mapperName",mapperName);
            entry.put("entityPackageName",entityPackageName);
            entry.put("mapperPackageName",mapperPackageName);
            entry.put("servicePackageName",servicePackageName);

            //是否为mp模式
            if(GeneratorMode.MYBATIS_PLUS.equals(generatorConfig.getMode())){
                entry.put("mybatisPlusFlag",true);
                entry.put("entityPackageName",packageName+".entity."+entityName);
                entry.put("entityName",entityName);
            }else{
                entry.put("mybatisPlusFlag",false);
            }

            //作者
            if(null==generatorConfig.getAuthor()){
                entry.put("author","ttfor");
            }else{
                entry.put("author",generatorConfig.getAuthor());
            }
            //生成日期
            entry.put("date",new Date());
            classNames.add(serviceImplName);
            entityData.put(filePath.get(serviceImplName),entry);
        }
        return new FillConfig(classNames,entityData);
    }
    //controller.java
    private FillConfig buildControllerData(UrlConfig urlConfig, List<Table> tables){
        Map<String,Map<String,Object>> entityData = new HashMap<>();
        List<String> classNames = new ArrayList<>();
        Map<String, String> filePath = urlConfig.getFilePath();
        for (Table table : tables) {
            //填充entity所需的配置
            Map<String,Object> entry = new HashMap<>();
            String entityName = NameUtil.buildClassName(table.getTableName());
            String controllerName = entityName + "Controller";
            String packageName = urlConfig.getPackageName();
            String accessPath = NameUtil.buildFieldName(table.getTableName());

            entry.put("packageName",packageName+".controller");
            entry.put("controllerName",controllerName);
            entry.put("accessPath",accessPath);

            //作者
            if(null==generatorConfig.getAuthor()){
                entry.put("author","ttfor");
            }else{
                entry.put("author",generatorConfig.getAuthor());
            }
            //生成日期
            entry.put("date",new Date());
            classNames.add(controllerName);
            entityData.put(filePath.get(controllerName),entry);
        }
        return new FillConfig(classNames,entityData);
    }

    //生成entity数据
    private void generateEntity(Configuration configuration,UrlConfig urlConfig,List<Table> tables) throws IOException, TemplateException {
        fill(urlConfig,configuration.getTemplate("/templates/entity.ftl"),
                buildEntityData(urlConfig, tables));
    }

    //生成mapper数据
    private void generateMapper(Configuration configuration,UrlConfig urlConfig,List<Table> tables) throws IOException, TemplateException {
        fill(urlConfig,configuration.getTemplate("/templates/mapper.java.ftl"),
                buildMapperData(urlConfig, tables));
    }

    //生成service数据
    private void generateService(Configuration configuration,UrlConfig urlConfig,List<Table> tables) throws IOException, TemplateException {
        fill(urlConfig,configuration.getTemplate("/templates/service.java.ftl"),
                buildServiceData(urlConfig, tables));
    }

    //生成serviceImpl数据
    private void generateServiceImpl(Configuration configuration,UrlConfig urlConfig,List<Table> tables) throws IOException, TemplateException {
        fill(urlConfig,configuration.getTemplate("/templates/serviceImpl.java.ftl"),
                buildServiceImplData(urlConfig, tables));
    }

    //生成controller数据
    private void generateControllerImpl(Configuration configuration,UrlConfig urlConfig,List<Table> tables) throws IOException, TemplateException {
        fill(urlConfig,configuration.getTemplate("/templates/controller.java.ftl"),
                buildControllerData(urlConfig, tables));
    }

    private void fill(UrlConfig urlConfig,Template template,FillConfig fillConfig) throws IOException, TemplateException {
        List<String> classNames = fillConfig.getClassNames();
        Map<String, Map<String, Object>> fillConfigs = fillConfig.getConfigs();
        Map<String, String> filePath = urlConfig.getFilePath();
        for (String className : classNames) {
            String generatePath = filePath.get(className);
            Map<String, Object> data = fillConfigs.get(generatePath);
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(generatePath)));
            template.process(data,out);
            log.info("========================生成"+className+".java========================");
        }
    }


}
