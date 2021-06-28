//package com.ex;
//
//import com.ex.cofig.GeneratorConfig;
//import com.ex.generator.CodeGenerator;
//
//import java.io.InputStream;
//
//public class Generate {
//    public static void main(String[] args) throws Exception {
//        GeneratorConfig config = new GeneratorConfig();
//        config.setRootPath("F:\\Java\\ispringboot\\model\\src\\main\\java");
//        config.setPackageName("com.ex");
//        InputStream in = CodeGenerator.class.getClassLoader().getResourceAsStream("druid.properties");
//        config.buildDatabaseConfig(in);
//        config.selectTable("product");
//        CodeGenerator codeGenerator = new CodeGenerator(config);
//        codeGenerator.generate();
//    }
//}
