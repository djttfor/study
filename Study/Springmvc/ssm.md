# SSM

## 1.SSM Annotation配置  

spring + springmvc + mybatis plus

### 1.1 pom.xml 

#### 1.1.1父工程配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>dj.study</groupId>
    <artifactId>springmvc_final</artifactId>
    <packaging>pom</packaging>
    <version>1.0.0</version>
    <modules>
        <module>ssm_annotation</module>
        <module>servlet_demo</module>
    </modules>

     <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <log4j2.version>2.13.3</log4j2.version>
        <spring.version>5.2.5.RELEASE</spring.version>
        <druid.version>1.1.23</druid.version>
        <mysql.version>5.1.32</mysql.version>
        <mybatis.version>3.5.5</mybatis.version>
        <mybatis-spring>2.0.6</mybatis-spring>
        <mybatis-plus>3.4.2</mybatis-plus>
        <my.generator.version>1.0.0</my.generator.version>
        <junit5.version>5.2.0</junit5.version>
        <servlet.version>3.1.0</servlet.version>
        <aspectj.version>1.9.6</aspectj.version>
        <fastjson.version>1.2.73</fastjson.version>
        <tomcat.version>8.5</tomcat.version>
        <commons-fileupload>1.3.1</commons-fileupload>
        <commons-io.version>2.4</commons-io.version>
        <jsr250-api.version>1.0</jsr250-api.version>
        <pagehelper.version>5.1.2</pagehelper.version>
    </properties>


    <dependencyManagement>
        <dependencies>
            <!--spring全家桶-->
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-webmvc</artifactId>
                <version>${spring.version}</version>
            </dependency>
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-context</artifactId>
                <version>${spring.version}</version>
            </dependency>
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-test</artifactId>
                <version>${spring.version}</version>
            </dependency>
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-jdbc</artifactId>
                <version>${spring.version}</version>
            </dependency>
            <dependency>
                <groupId>org.aspectj</groupId>
                <artifactId>aspectjweaver</artifactId>
                <version>${aspectj.version}</version>
            </dependency>
            <!--log4j2-->
            <dependency>
                <groupId>org.apache.logging.log4j</groupId>
                <artifactId>log4j-slf4j-impl</artifactId>
                <version>${log4j2.version}</version>
            </dependency>
            <!--数据库连接池-->
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid</artifactId>
                <version>${druid.version}</version>
            </dependency>
            <!--mysql数据库驱动-->
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>${mysql.version}</version>
            </dependency>
            <!--mybatis-plus-->
            <dependency>
                <groupId>com.baomidou</groupId>
                <artifactId>mybatis-plus</artifactId>
                <version>${mybatis-plus}</version>
            </dependency>
            <!--自定义的代码生成器-->
            <dependency>
                <groupId>com.ex</groupId>
                <artifactId>generator</artifactId>
                <version>${my.generator.version}</version>
            </dependency>
            <!--junit5-->
            <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter-engine</artifactId>
                <version>${junit5.version}</version>
                <scope>test</scope>
            </dependency>
            <!--servlet3.0-->
            <dependency>
                <groupId>javax.servlet</groupId>
                <artifactId>javax.servlet-api</artifactId>
                <version>${servlet.version}</version>
            </dependency>

           <!--aspectj-->
            <dependency>
                <groupId>org.aspectj</groupId>
                <artifactId>aspectjweaver</artifactId>
                <version>${aspectj.version}</version>
            </dependency>

            <!--fastjson-->
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>fastjson</artifactId>
                <version>${fastjson.version}</version>
            </dependency>
            <!--文件上传-->
            <dependency>
                <groupId>commons-fileupload</groupId>
                <artifactId>commons-fileupload</artifactId>
                <version>${commons-fileupload}</version>
            </dependency>
            <!--jsr250-->
            <dependency>
                <groupId>javax.annotation</groupId>
                <artifactId>jsr250-api</artifactId>
                <version>${jsr250-api.version}</version>
            </dependency>
            <!--分页插件pagehelper-->
            <dependency>
                <groupId>com.github.pagehelper</groupId>
                <artifactId>pagehelper</artifactId>
                <version>${pagehelper.version}</version>
            </dependency>
            <!--tomcat-->
            <dependency>
                <groupId>org.apache.tomcat</groupId>
                <artifactId>Tomcat8.5</artifactId>
                <version>${tomcat.version}</version>
            </dependency>
            <!--文件上传-->
              <dependency>
                <groupId>commons-io</groupId>
                <artifactId>commons-io</artifactId>
                <version>${commons-io.version}</version>
            </dependency>
        </dependencies>
    </dependencyManagement>

</project>
```

#### 1.1.2 子项目配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>springmvc_final</artifactId>
        <groupId>dj.study</groupId>
        <version>1.0.0</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>ssm_annotation</artifactId>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>
    <packaging>war</packaging>

    <dependencies>
        <!--mybatis-plus-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus</artifactId>
        </dependency>
        <!--log4j2-->
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-slf4j-impl</artifactId>
        </dependency>
        <!--junit5-->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <scope>test</scope>
        </dependency>
        <!--spring-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
        </dependency>
        <!--aspectj-->
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
        </dependency>
        <!--fastjson-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
        </dependency>
        <!--mysql驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <!--druid连接池-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
        </dependency>
<!--        代码生成器-->
        <dependency>
            <groupId>com.ex</groupId>
            <artifactId>generator</artifactId>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.tomcat</groupId>
            <artifactId>Tomcat8.5</artifactId>
        </dependency>

    </dependencies>

</project>
```

### 1.2 spring 配置

```java
package com.ex.ssm.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.ex.ssm.plugins.SQLExtractLog;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.*;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

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

        //mybatisplus自带的分页插件
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

    @Bean
    public FastJsonHttpMessageConverter createFJHMConverter(){
        return new FastJsonHttpMessageConverter();
    }

    @Bean
    public DataSourceTransactionManager createTransactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
}
```

### 1.3 springmvc 配置

#### 1.3.1 spring子容器配置

```java
package com.ex.ssm.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@EnableWebMvc
@Configuration
@ComponentScan("com.ex.ssm.controller")
public class ISpringMvcConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new FastJsonHttpMessageConverter());
    }
    
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    
}
```

#### 1.3.2 springmvc配置

```java
package com.ex.ssm.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class MyWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
       super.onStartup(servletContext);
       FilterRegistration.Dynamic cFilter =
               servletContext.addFilter("characterEncodingFilter", CharacterEncodingFilter.class);
       cFilter.setInitParameter("encoding","UTF-8");
       cFilter.setInitParameter("forceEncoding", "true");
       cFilter.addMappingForUrlPatterns(null,false,"/*");


//        EnumSet<DispatcherType> dispatcherTypes = EnumSet.allOf(DispatcherType.class);
//        dispatcherTypes.add(DispatcherType.REQUEST);
//        dispatcherTypes.add(DispatcherType.FORWARD);
        //cFilter.addMappingForUrlPatterns(dispatcherTypes,true,"/*");

//        ServletRegistration aDefault = servletContext.getServletRegistration("default");
//        aDefault.addMapping("/page/*");
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

//    @Override
//    protected Filter[] getServletFilters() {
//        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//        characterEncodingFilter.setEncoding("UTF-8");
//        return new Filter[]{characterEncodingFilter};
//    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{ISpringConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{ISpringMvcConfig.class};
    }
}
```

#### 1.3.3 druid 配置

```java
package com.ex.ssm.config;

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
#缓存PreparedStatement，默认false
druid.poolPreparedStatements=true
#缓存PreparedStatement的最大数量，默认-1（不缓存）。大于0时会自动开启缓存PreparedStatement，所以可以省略上一句设置
druid.maxOpenPreparedStatements=20
```

#### 1.3.4 log4j2配置

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

        <!-- 文件会打印出所有信息，这个log每次运行程序会自动清空，由append属性决定，这个也挺有用的，适合临时测试用 -->
        <!-- append为TRUE表示消息增加到指定文件中，false表示消息覆盖指定的文件内容，默认值是true -->
<!--        <File name="log" fileName="log/test.log" append="false">-->
<!--            <PatternLayout pattern="[%d{HH:mm:ss.SSS}] [%-5p] %l - %m%n"/>-->
<!--        </File>-->

        <!-- 添加过滤器ThresholdFilter,可以有选择的输出某个级别以上的类别  onMatch="ACCEPT" onMismatch="DENY"意思是匹配就接受,否则直接拒绝  -->
<!--        <File name="ERROR" fileName="logs/error.log">-->
<!--            <ThresholdFilter level="error" onMatch="ACCEPT" onMismatch="DENY"/>-->
<!--            <PatternLayout pattern="[%d{yyyy.MM.dd 'at' HH:mm:ss z}] [%-5p] %l - %m%n"/>-->
<!--        </File>-->

        <!-- 这个会打印出所有的信息，每次大小超过size，则这size大小的日志会自动存入按年份-月份建立的文件夹下面并进行压缩，作为存档 -->
<!--        <RollingFile name="RollingFile" fileName="logs/web.log"-->
<!--                     filePattern="logs/$${date:yyyy-MM}/web-%d{MM-dd-yyyy}-%i.log.gz">-->
<!--            <PatternLayout pattern="[%d{yyyy-MM-dd 'at' HH:mm:ss z}] [%-5p] %l - %m%n"/>-->
<!--            <SizeBasedTriggeringPolicy size="2MB"/>-->
<!--        </RollingFile>-->
    </appenders>

    <!-- 然后定义logger，只有定义了logger并引入的appender，appender才会生效 -->
    <loggers>
        <!-- 过滤掉spring和mybatis的一些无用的DEBUG信息-->
<!--        <logger name="org.springframework" level="INFO"/>-->
<!--        <logger name="org.mybatis" level="INFO"/>-->
        <!-- 建立一个默认的root的logger -->
        <root level="debug">
<!--            <appender-ref ref="RollingFile"/>-->
<!--            <appender-ref ref="ERROR"/>-->
<!--            <appender-ref ref="log"/>-->
            <appender-ref ref="Console"/>
        </root>
    </loggers>

</configuration>
```

## 2 . SSM Xml配置

### 1. spring配置

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
</beans>
```

### 2.spring-mvc 配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
    <context:component-scan base-package="com.ex.ssm.controller"  />

    <!--视图解析器-->
    <bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/page/"/>
        <property name="suffix" value=".jsp"/>
    </bean>

    <!--消息转换器-->
    <mvc:annotation-driven conversion-service="converterService">
        <mvc:message-converters register-defaults="true">
            <bean class="com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter">
                <property name="supportedMediaTypes" >
                    <list>
                        <value>application/json</value>
                        <value>application/x-www-form-urlencoded</value>
                    </list>
                </property>
                <property name="fastJsonConfig" ref="fastJsonConfig"/>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>

    <bean id="fastJsonConfig" class="com.alibaba.fastjson.support.config.FastJsonConfig">
        <property name="charset" value="UTF-8" />
    </bean>

    <!-- 配置类型转换器工厂 -->
    <bean id="converterService"
          class="org.springframework.context.support.ConversionServiceFactoryBean">
        <!-- 给工厂注入一个新的类型转换器 -->
        <property name="converters">
            <array>
                <!-- 配置自定义类型转换器 -->
                <bean class="com.ex.ssm.config.StringToDate"/>
                <bean class="com.ex.ssm.config.DateToString"/>
            </array>
        </property>
    </bean>

    <!--静态资源不拦截-->
    <mvc:resources mapping="/page/**" location="/page/"/>
    <mvc:resources mapping="/js/**" location="/js/"/>
    <mvc:resources mapping="/image/**" location="/image/"/>

    <!--异常处理器-->
    <bean id="exceptionHandle" class="com.ex.ssm.config.MyExceptionHandle"/>

    <!--拦截器-->
    <mvc:interceptors>
        <mvc:interceptor>
            <mvc:mapping path="/**"/>
            <mvc:exclude-mapping path="/haha/**"/>
            <mvc:exclude-mapping path="/page/**"/>
            <mvc:exclude-mapping path="/js/**"/>
            <mvc:exclude-mapping path="/image/**"/>
            <bean class="com.ex.ssm.config.DemoInterceptor"/>
        </mvc:interceptor>
        <mvc:interceptor>
            <mvc:mapping path="/haha/login"/>
            <bean class="com.ex.ssm.config.LoginInterceptor"/>
        </mvc:interceptor>
    </mvc:interceptors>


    <!--文件上传设置-->
    <bean id="multipartResolver"
          class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
        <!--上传文件的最大大小，单位为字节 -->
        <property name="maxUploadSize" value="17367648787"/>

        <!-- 上传文件的编码 -->
        <property name="defaultEncoding" value="UTF-8"/>
    </bean>
</beans>
```

### 3.web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">
    <display-name>Archetype Created Web Application</display-name>
    <!--告诉他spring.xml在哪里-->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:spring.xml</param-value>
    </context-param>
    <!--监听spring启动-->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
      
     <!--配置dispatcher-->
    <servlet>
        <servlet-name>dispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:spring-mvc.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>dispatcherServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
     <!--字符编码过滤-->
    <filter>
        <filter-name>characterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>characterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

</web-app>
```

### 4. Date转换器

需在spring-mvc.xml中配置才生效

```java
public class DateToString implements Converter<Date,String > {
    @Override
    public String convert(Date source) {
        DateFormat dateFormat;
        if(!ObjectUtils.isEmpty(source)){
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(source);
        }
        return null;
    }
}
public class StringToDate implements Converter<String, Date> {
    private static final Logger logger = LoggerFactory.getLogger(StringToDate.class);
    @Override
    public Date convert(String source) {
        SimpleDateFormat simpleDateFormat ;
        if(!StringUtils.isEmpty(source)){
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return simpleDateFormat.parse(source);
            } catch (ParseException e) {
                logger.info("这个值不能转为日期,Value:"+source,e);
            }
        }
        return null;
    }
}

```

### 5. 异常处理器

在spring-mvc.xml中注入即可

```java
public class MyExceptionHandle implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mv = new ModelAndView();

        Throwable realException = ExceptionUtil.getRealException(ex);

        mv.addObject("stackInfo",ExceptionUtil.getStackTrace(realException));

        mv.setViewName("404");
        return mv;
    }
}
```

##  整合JUnit5

```java
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ISpringConfig.class})
```

## Restful 风格

```java
@GetMapping("/test1/{a}/{b}")
public void test1(@PathVariable(value = "a") int a, @PathVariable(value = "b",required = false) int b){
    log.info("这是Restful风格的访问参数：{},{}",a,b);
}
```

```java
//分页查询配上restful风格
@PostMapping("/all/{current}/{pageSize}")
public Page<User> all(@PathVariable("current") int current,@PathVariable("pageSize") int pageSize){
    return userService.selectPage(current,pageSize);
}
```

```java
//分页模糊查询
@PostMapping("/like/{current}/{pageSize}")
public Page<User> selectLikeName(@RequestBody User user,
                                 @PathVariable("current") int current,@PathVariable("pageSize") int pageSize){
    return userService.selectLikePage(user,current,pageSize);
}
```

```http
//IDEA 接口测试工具
POST http://localhost:8081/ssm/user/like/1/5
Accept: application/json
Content-Type: application/json

{
"username":"张"
}

```