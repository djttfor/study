# mybatis-spring

## 1.maven配置

mybatis 版本3.5.5 ，mybatis-spring版本2.0.6

### 1.父工程pom.xml

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
        <module>ssm_xml</module>
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
        <jsr250-api.version>1.0</jsr250-api.version>
        <pagehelper.version>5.1.2</pagehelper.version>
    </properties>


    <dependencyManagement>
        <dependencies>
            <!--spring全家桶-->
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

            <!--mybatis-->
            <dependency>
                <groupId>org.mybatis</groupId>
                <artifactId>mybatis</artifactId>
                <version>${mybatis.version}</version>
            </dependency>
            <!--mybatis-spring-->
            <dependency>
                <groupId>org.mybatis</groupId>
                <artifactId>mybatis-spring</artifactId>
                <version>${mybatis-spring}</version>
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
        </dependencies>
    </dependencyManagement>

</project>
```

### 2.子项目pom.xml

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

    <artifactId>ssm_xml</artifactId>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>
    <packaging>war</packaging>

    <dependencies>
        <!--mybatis-plus-->
<!--        <dependency>-->
<!--            <groupId>com.baomidou</groupId>-->
<!--            <artifactId>mybatis-plus</artifactId>-->
<!--        </dependency>-->

        <!--mybatis-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
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
    </dependencies>
</project>
```

## 2.spring 配置

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
    <bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="druidDataSource"/>
        <property name="mapperLocations" value="classpath:mapper/*Mapper.xml"/>
        <property name="typeAliasesPackage" value="com.ex.ssm.entity"/>
        <property name="configuration" ref="mybatisConfig"/>
        <property name="plugins" ref="sqlExtractLog"/>
    </bean>
    <!--mapper配置-->
    <bean id="mapperScannerConfigurer" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.ex.ssm.mapper"/>
<!--        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactoryBean"/>-->
    </bean>
    <!--打印sql语句-->
    <bean id="sqlExtractLog" class="com.ex.ssm.plugins.SQLExtractLog"/>
    <!--关闭二级缓存-->
    <bean id="mybatisConfig" class="org.apache.ibatis.session.Configuration">
        <property name="cacheEnabled" value="false"/>
    </bean>  
</beans>
```

## 3.druid.properties

```properties
druid.url=jdbc:mysql://192.168.8.129:3306/base?characterEncoding=UTF8
druid.driverClassName=com.mysql.jdbc.Driver
druid.username=root
druid.password=1998
druid.initialSize=10
druid.maxActive=30
druid.minIdle=10
druid.maxWait=2000
```

## 4.log4j2.xml

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
    <!-- 然后定义logger，只有定义了logger并引入的appender，appender才会生效 -->
    <loggers>
        <!-- 建立一个默认的root的logger -->
        <root level="debug">
            <appender-ref ref="Console"/>
        </root>
    </loggers>
</configuration>
```

## 5.mapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.ex.ssm.mapper.UserMapper">
    <resultMap id="all" type="user">
        <id column="id" property="id"/>
    </resultMap>
    <select id="selectAll" resultMap="all">
        select * from user
    </select>
</mapper>
```