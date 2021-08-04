### 1.1 maven依赖

```xml
   <!--spring-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.2.5.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>5.2.5.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>5.2.5.RELEASE</version>
        </dependency>
        <!--mybatis-plus-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus</artifactId>
            <version>3.4.2</version>
        </dependency>
        <!--log4j2-->
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-slf4j-impl</artifactId>
            <version>2.13.3</version>
        </dependency>
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-api</artifactId>
            <version>2.13.3</version>
        </dependency>
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-core</artifactId>
            <version>2.13.3</version>
        </dependency>

        <!--junit5-->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>5.2.0</version>
            <scope>test</scope>
        </dependency>
        <!--mysql驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.32</version>
        </dependency>
        <!--druid连接池-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.23</version>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.4</version>
        </dependency>

        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.9.6</version>
        </dependency>
```



### 1.2 spring配置

#### 1.2.1 java配置

```java
@Configuration
@ComponentScan("com.ex")
@EnableTransactionManagement
@EnableAspectJAutoProxy
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
    @Bean("sqlSessionFactory")
    public MybatisSqlSessionFactoryBean getSqlSessionFactoryBean(DataSource dataSource) throws IOException {
        MybatisSqlSessionFactoryBean msfb = new MybatisSqlSessionFactoryBean();
        msfb.setDataSource(dataSource);
        msfb.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/*Mapper.xml"));
        msfb.setTypeAliasesPackage("com.ex.ssm.entity");

        //mybatisplus自带的插件
        PaginationInnerInterceptor p =new PaginationInnerInterceptor(DbType.MYSQL);
        MybatisPlusInterceptor mpi = new MybatisPlusInterceptor();
        mpi.addInnerInterceptor(p);

        msfb.setPlugins(new SQLExtractLog(),mpi);
        return msfb;
    }
    //@MapperScan("com.ex.ssm.mapper")可代替下面的方法
    @Bean
    public MapperScannerConfigurer getMapperScannerConfigurer(){
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        msc.setBasePackage("com.ex.ssm.mapper");
        return msc;
    }
    @Bean
    public TransactionManager getTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
```

#### 1.2.2 druid.properties配置

```java
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

}
```



```properties
druid.url=jdbc:mysql://localhost:3306/base?characterEncoding=UTF8
#可以缺省
druid.driverClassName=com.mysql.jdbc.Driver
druid.username=root
druid.password=1998


##初始连接数，默认0
druid.initialSize=10
#最大连接数，默认8
druid.maxActive=30
#最小闲置数
druid.minIdle=10
#获取连接的最大等待时间，单位毫秒
druid.maxWait=2000
```



#### 1.2.3 xml配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/aop/spring-aop.xsd
        http://www.springframework.org/schema/tx
        https://www.springframework.org/schema/tx/spring-tx.xsd">

    <!--包扫描-->
    <context:component-scan base-package="com.ex.ssm">
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
        <context:exclude-filter type="annotation" expression="org.springframework.web.bind.annotation.RestController"/>
    </context:component-scan>

    <!--druid数据源配置-->
    <context:property-placeholder location="classpath:druid.properties"/>
    <bean name="druidDataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="url" value="${druid.url}" />
        <property name="driverClassName" value="${druid.driverClassName}" />
        <property name="username" value="${druid.username}" />
        <property name="password" value="${druid.password}" />
        <property name="initialSize" value="${druid.initialSize}"/>
        <property name="maxActive" value="${druid.maxActive}" />
        <property name="minIdle" value="${druid.minIdle}" />
        <property name="maxWait" value="${druid.maxWait}" />
     </bean>
    <!--mybatis配置-->
    <bean id="sqlSessionFactoryBean" class="com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean">
        <property name="dataSource" ref="druidDataSource"/>
        <property name="mapperLocations" value="classpath:mapper/*Mapper.xml"/>
        <property name="typeAliasesPackage" value="com.ex.ssm.entity"/>
        <property name="plugins" >
            <array>
                <ref bean="sqlExtractLog"/>
                <ref bean="mybatisPlusInterceptor"/>
            </array>
        </property>
    </bean>
    <!--mybati-plus分页拦截器-->
    <bean id="paginationInnerInterceptor" class="com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor">
        <constructor-arg name="dbType" value="MYSQL"/>
    </bean>
    <!--mybatis-plus拦截器-->
    <bean id="mybatisPlusInterceptor" class="com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor">
        <property name="interceptors">
            <array>
                <ref bean="paginationInnerInterceptor"/>
            </array>
        </property>
    </bean>

    <!--mapper配置-->
    <bean id="mapperScannerConfigurer" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.ex.ssm.mapper"/>
    </bean>
    <!--打印sql语句-->
    <bean id="sqlExtractLog" class="com.ex.ssm.plugins.SQLExtractLog"/>

    <!--启用aop自动代理-->
    <aop:aspectj-autoproxy/>
    <!--配置事务管理器-->
    <bean id="transactionManager"
          class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="druidDataSource" />
    </bean>
    <!--启用声明式事务-->
    <tx:annotation-driven />
    <!--aop基础配置,配置aop作用在那个类-->
    <aop:config>
        <aop:pointcut id="txAdvices" expression="execution(* com.ex.ssm.service.impl.*.*(..))"/>
        <aop:advisor advice-ref="myAdvice" pointcut-ref="txAdvices" />
    </aop:config>
    <!--配置事务作用的方法以及事务的行为-->
    <tx:advice id="myAdvice">
        <tx:attributes>
            <tx:method name="find*" read-only="true" />
            <tx:method name="tr*" no-rollback-for="java.lang.RuntimeException"/>
        </tx:attributes>
    </tx:advice>
</beans>

```

### 1.3 使用mybatis-plus写xml

crud配置

```java
public interface UserService extends IService<User>
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService 
public interface UserMapper extends BaseMapper<User>
```

xml配置

```xml
<select id="queryAccountByMybatisplus" resultMap="byUid">
    select account_name ,password ,balance from account ${ew.customSqlSegment}
</select>
```

mapper配置

```java
Account queryAccountByMybatisplus(@Param(Constants.WRAPPER)Wrapper<Account> ew);
```

### 1.4 使用mybatis plus自带的分页插件

```java
    //mybatisplus自带的插件
        PaginationInnerInterceptor p =new PaginationInnerInterceptor(DbType.MYSQL);
        MybatisPlusInterceptor mpi = new MybatisPlusInterceptor();
        mpi.addInnerInterceptor(p);
        msfb.setPlugins(mpi);
```

```java
Page<User> page = new Page<>(1,5);//当前页，每页记录数
Page<User> userPage = userMapper.selectPage(page, Wrappers.lambdaQuery());
log.info("查询到的记录:{}",userPage.getRecords());
log.info("当前页:{}",userPage.getCurrent());
log.info("每页显示条数:{}",userPage.getSize());
log.info("总条数:{}",userPage.getTotal());
```

### 1.5 使用pagehelper 进行分页

```xml
<dependency>
                <groupId>com.github.pagehelper</groupId>
                <artifactId>pagehelper</artifactId>
                <version>5.1.2</version>
            </dependency>

```

```java
 //注册pagehelper插件
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect","mysql");
        properties.setProperty("reasonable","true");
        pageInterceptor.setProperties(properties);

PageHelper.startPage(1,5);//必须放在最前面，两个参数分别是当前页码，页面记录数，放在ThreadLocal上面。
com.github.pagehelper.Page<User> page = (com.github.pagehelper.Page<User>) userService.list();
log.info("当前页:{}",page.getPageNum());
log.info("每页显示条数:{}",page.getPageSize());
log.info("总条数:{}",page.getTotal());

for (User user : page.getResult()) {
    log.info("查询结果:{}",user);
}
```

```xml
//分页只能选其一来用，用mybatis-plus分页，就不要导入pagehelper，如果坚持想用pagehelper，那么加入以下排除
<dependency>
                <groupId>com.baomidou</groupId>
                <artifactId>mybatis-plus</artifactId>
                <exclusions>
                    <exclusion>
                        <groupId>com.github.jsqlparser</groupId>
                        <artifactId>jsqlparser</artifactId>
                    </exclusion>
                </exclusions>
                <version>${mybatis-plus}</version>
            </dependency>
```

### 1.6 自定义sql语句

直接写就完事了

```java
@Select("select count(*) from user")
int findCount();
```

### 1.7 逻辑分页与物理分页



![image-20210309155048060](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210309155048060.png)

### 1.8  选择字段查询

```java
//service方法使用lqw的select方法选择字段
public Long getBalance(Integer id){
    LambdaQueryWrapper<Account> select = getLqw()
        .select(Account::getBalance).eq(Account::getId,id);
    Account one = getOne(select);
    return one.getBalance();
}
//非lam方式
 public Long getBalance(Integer id){
        QueryWrapper<Account> select = getQw().select("balance").eq("id",id);
        Account one = getOne(select);
        return one.getBalance();
}
//如果predicate.test() = true,那么选择该字段
select(Predicate<TableFieldInfo> predicate)
//例如 ，如下将显示balance字段与password字段
select(i->i.getProperty().equals("balance")||i.getProperty().equals("password"))
//这个方法效果同上
select(Class<T> entityClass, Predicate<TableFieldInfo> predicate)
```

### 1.9 mybatis插件

springboot使用时在mybatis-plus配置类下注入bean即可

#### executor插件

```java
@Intercepts(
        {
            @Signature(type = Executor.class,method = "query",args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
            @Signature(type = Executor.class,method = "query",args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
            @Signature(type = Executor.class,method = "update",args = {MappedStatement.class, Object.class})
        }
)
@Slf4j
public class ExecutorPrintSQLPlugin implements Interceptor {
    @Override
    public  Object  intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object params = args[1];
        BoundSql boundSql;
        if(args.length==4||args.length==2){
            boundSql =  ms.getBoundSql(params);
        }else{
            boundSql = (BoundSql) args[5];//获取分页插件的sql
        }
        String message = getSqlStr(boundSql,ms);
        log.info("Sql语句:"+message);
        return invocation.proceed();
    }
    public String getSqlStr(BoundSql boundSql,MappedStatement ms){
        StringBuilder logStr = new StringBuilder();
        logStr.append(boundSql.getSql().replaceAll("\n|\\s+"," "));
        Object parameterObject = null;
        try{
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
            if (parameterMappings.size()>0 &&
                    (null != ( parameterObject = boundSql.getParameterObject()))) {
                logStr.append("\n").append(" Parameters:");

                TypeHandlerRegistry typeHandlerRegistry = ms.getConfiguration().getTypeHandlerRegistry();

                MetaObject metaObject = new Configuration().newMetaObject(parameterObject);
                for(int i=0;i<parameterMappings.size();i++){
                    Object value ;
                    ParameterMapping parameterMapping = parameterMappings.get(i);
                    String property = parameterMapping.getProperty();
                    //sql执行只需要一个参数的时候
                    if (boundSql.hasAdditionalParameter(property)) {
                        value = boundSql.getAdditionalParameter(property);
                    } else if(typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())){
                        value = parameterObject;
                    }else{
                        //多个参数
                        value = metaObject.getValue(property);
                    }
                    String simpleName = value.getClass().getSimpleName();
                    if(i==parameterMappings.size()-1){
                        logStr.append(value).append("(").append(simpleName).append(").");
                    }else{
                        logStr.append(value).append("(").append(simpleName).append(")").append(",");
                    }
                }
            }
        }catch (Exception e){
            log.info("SQL记录插件出错了,当前参数为{},异常信息:{}",parameterObject,e);
        }
        return logStr.toString();
    }
}
```

## 2. mybatis-plus代码生成器

依赖

```xml
<!--代码生成器-->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-generator</artifactId>
    <version>3.4.1</version>
</dependency>
<!--模板引擎-->
<dependency>
    <groupId>org.apache.velocity</groupId>
    <artifactId>velocity-engine-core</artifactId>
    <version>2.2</version>
</dependency>
 <!--mybatis-plus不需要再导入mybaits与mybatis-spring了-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus</artifactId>
            <version>3.4.2</version>
        </dependency>
<!--log4j2-->
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-slf4j-impl</artifactId>
            <version>2.13.3</version>
        </dependency>
```

xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration status="debug" monitorInterval="30">
    <!-- 先定义所有的appender(附加器)-->
    <appenders>
        <!-- 输出控制台的配置 -->
        <Console name="Console" target="SYSTEM_OUT">
            <!-- 控制台只输出level及以上级别的信息（onMatch），其他的直接拒绝（onMismatch） -->
            <ThresholdFilter level="debug" onMatch="ACCEPT" onMismatch="DENY"/>
            <!-- 输出日志的格式 -->
            <PatternLayout pattern="[%d{HH:mm:ss.SSS}] [%-5p] %l - %m%n"/>
        </Console>
    </appenders>

    <loggers>
        <root level="debug">
            <appender-ref ref="Console"/>
        </root>
    </loggers>

</configuration>
```

代码

```java
public class CodeGenerator {
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("F:\\Java\\iyeb\\generator\\src\\main\\java");
        gc.setAuthor("ttfor");
        gc.setFileOverride(false);// 是否覆盖同名文件，默认是false
        gc.setActiveRecord(false);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(false);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setServiceName("%sService");


        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/iyeb?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("1998");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        // pc.setModuleName(scanner("smp_01"));
        pc.setParent("com.ex.server");
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
        strategy.setTablePrefix("t_");// 去除表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        //strategy.setInclude("user","account"); // 需要生成的表
        strategy.setEntityLombokModel(true);
        mpg.setStrategy(strategy);
        mpg.execute();
    }
}

```
