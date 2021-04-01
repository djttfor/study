### 1.1 maven依赖

详见mybatis-plus-maven依赖

### 1.2 spring配置

#### 1.2.1 java配置

```java
@Configuration
@ComponentScan(value = "com.ex" ,excludeFilters = {@ComponentScan.Filter({Controller.class})})
@PropertySource("classpath:druid.properties")
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

#### 1.2.2 xml配置

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
```

代码

```java
public class CodeGenerator {
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("D:\\Java\\study\\smp_01\\src\\main\\java");
        gc.setAuthor("ttfor");
        gc.setFileOverride(false);// 是否覆盖同名文件，默认是false
        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/base?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("1998");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
       // pc.setModuleName(scanner("smp_01"));
        pc.setParent("com.ex.smp");
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
        //strategy.setTablePrefix(new String[] { "user_" });// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(new String[] { "user","account" }); // 需要生成的表
         mpg.setStrategy(strategy);
        mpg.execute();
    }
}
```

