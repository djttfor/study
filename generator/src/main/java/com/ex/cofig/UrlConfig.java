package com.ex.cofig;

import com.ex.database.entity.Table;
import com.ex.util.NameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建所有路径
 */

public class UrlConfig {
    private final String rootPath;
    private final String packageName;
    private final String completePath;

    private final Map<String,String> packagePath = new HashMap<>();

    private final Map<String,String> filePath = new HashMap<>();


    public String getRootPath() {
        return rootPath;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getCompletePath() {
        return completePath;
    }



    public Map<String, String> getPackagePath() {
        return packagePath;
    }
    public Map<String, String> getFilePath() {
        return filePath;
    }

    private static final Logger log = LoggerFactory.getLogger(UrlConfig.class);


    public UrlConfig(String rootPath, String packageName, List<Table> tables,boolean requireController){
        if(rootPath==null||packageName==null){
            throw new RuntimeException("包名与项目路径不能为空");
        }
        this.rootPath = rootPath;
        this.packageName = packageName;
        this.completePath = rootPath +"\\"+ packageName.replace('.','\\');
        packagePath.put("Entity",completePath+"\\entity");
        packagePath.put("Mapper",completePath+"\\mapper");
        packagePath.put("Service",completePath+"\\service");
        packagePath.put("ServiceImpl",completePath+"\\service\\impl");
        if(requireController)
        packagePath.put("Controller",completePath+"\\controller");

        for (Table table : tables) {
            String entityName = NameUtil.buildClassName(table.getTableName());
            filePath.put(entityName,packagePath.get("Entity")+"\\"+entityName+".java");
            filePath.put(entityName+"Mapper",packagePath.get("Mapper")+"\\"+entityName+"Mapper.java");
            filePath.put(entityName+"Service",packagePath.get("Service")+"\\"+entityName+"Service.java");
            filePath.put(entityName+"ServiceImpl",packagePath.get("ServiceImpl")+"\\"+entityName+"ServiceImpl.java");
            if(requireController)
            filePath.put(entityName+"Controller",packagePath.get("Controller")+"\\"+entityName+"Controller.java");
        }
    }

    public void execute(){
        createAllPackagePath();
        createAllFile();
    }

    //创建所有目录
    private void createAllPackagePath(){
        createDirectory(completePath);//根目录不存在则创建
        packagePath.forEach((k,v)->{
            createDirectory(v);
        });
    }
    //创建所有文件
    private void createAllFile(){
        filePath.forEach((k,v)->{
            try {
                createFile(v);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private void createDirectory(String path){
        File rootFile = new File(path);
        if(!rootFile.exists()){
            if(!rootFile.mkdirs()){
                throw new RuntimeException("创建路径:"+path+"失败");
            }
        }
    }
    private void createFile(String fileName) throws IOException {
        File file = new File(fileName);
        if(!file.exists()){
            if(!file.createNewFile()){
                throw new RuntimeException("创建文件:"+fileName+"失败");
            }
        }
    }

}
