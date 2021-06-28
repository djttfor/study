## 依赖

```xml
 <dependency>
            <groupId>com.ex</groupId>
            <artifactId>generator</artifactId>
            <version>1.0.1</version>
        </dependency>
```

```java
public class Generator {
    public static void main(String[] args) throws Exception {
        //测试代码
        GeneratorConfig config = new GeneratorConfig();
        //改成自己的项目路径
        config.setRootPath("F:\\Java\\ispringboot\\rabbitmq_02\\src\\main\\java");
        config.setPackageName("com.ex");
        DatabaseConfig databaseConfig = new DatabaseConfig();
        databaseConfig.setDriverClassName("com.mysql.jdbc.Driver");
        databaseConfig.setUrl("jdbc:mysql://localhost:3306/base?characterEncoding=UTF8");
        databaseConfig.setUsername("root");
        databaseConfig.setPassword("1998");
        config.setDatabaseConfig(databaseConfig);
        config.selectTable("order","rider");
        CodeGenerator codeGenerator = new CodeGenerator(config);
        codeGenerator.generate();
    }
}

```

