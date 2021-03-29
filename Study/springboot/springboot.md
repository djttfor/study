

## 1.构建

### 1.基本构建

1.创建一个父工程，把除pom文件与IDEA配置文件以外的文件夹全部删除。父工程的pom仅做版本控制，一定要用IDEA的springboot模板创建

2.创建子模块，不要用springboot模板创建，如果你想作为启动模块作为jar执行，在pom.xml加入以下配置即可：

```xml
<dependencies>
    <!--web必需-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>   
      <!--启动必需-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
    <!--测试必须-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
 <!--打jar插件-->
<build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```

如果只想作为一个普普通通的模块，仅提供依赖，那么不用导入springboot相关。注意创建controller、service等包必须要与springboot主程序在同级路径。

除此之外，还需要在resources下面创建一个application.yml或者application.properties

4.打jar包，直接在maven父工程install即可，如果出现有如entity模块找不到，那么重新rebuild project，重新install

5.控制台运行springboot项目:安装好各个环境之后，到jar所在目录，输入命令java -jar ./xxx.jar，即可执行。

## 原理初探

### pom.xml

- spring-boot-dependencies: 核心依赖在父工程中！
- 我们在写或者引入一些springboot依赖的时候，不需要指定版本，就因为有这些版本仓库

### 启动器

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>
```

### 主程序

```java
@SpringBootApplication
public class SpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class,args);
    }
}
```



## YAML

yaml的基本使用，注意冒号前有空格

```yaml
#普通string赋值
name: jimmy

#对象
user:
  username: jimmy
  password: 123

#对象行内写法
user2: {username: jimmy,age: 3}

#数组
arr:
  - cat
  - dog
  - pig

#数组行内写法
arr2: [cat,dog,pig]


vo:
  name: ${random.uuid}
  age: 18
  birthday: 2020/9/9 18:35:66
  flag: false
  user: {name: jimmy,age: 20}
  names: ${arr2}
  users:
  - {name: kim,age: 20}
  - {name: jim,age: 30}
  nameMap: {aaa: bbb,ccc: ddd}
  userMap:
    aaa: {name: tom,age: 21}
    bbb: {name: jack,age: 22}
```

### yaml映射到实体类

```java
@Component
@ConfigurationProperties("vo")//如果只需要使用某个值而已，那只在那个字段上加@Value即可
public class Vo {
    String name;
    Integer age;
    Date birthday;
    boolean flag;
    User user;
    List<String> names;
    List<User> users;
    Map<String,String> nameMap;
    Map<String,User> userMap;
```

## 整合Junit

```xml
<!--测试必须-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

测试类的包名层级也要跟springboot启动类一致

```java
@SpringBootTest//加此注解即可
public class Test1 {
    @Autowired
    User user;
    @Autowired
    JdbcTemplate jdbcTemplate;
```

## 注解详解

### 1.ConditionalOnMissingBean

此注解在最好在自动配置类中使用，与@Bean配合使用，作用：当应用中不包含某个类时，配置才生效。例子如下：

```java
//测试类
@Autowired
UserService userService;
@Test
public void test4(){
    System.out.println(userService);
}
//Bean注册
@Bean
public UserService buildUserService(){
    return new UserService("默认创建");
}
/*
当前条件注解的作用就是应用中不包含UserService的Bean，才会执行这个方法。
所以将这两个方法顺序颠倒，那么b2创建判断当前没有UserService1这个Bean，就会执行成功，所以使用此注解时，注册顺序很重要。
*/
@Bean
@ConditionalOnMissingBean
public UserService buildUserService2(){
    return new UserService("第二次创建");
}

//当没有name为buildUserService2的Bean时，执行配置
@ConditionalOnMissingBean(name = "buildUserService2")

//当没有类型为AccountService的Bean时，执行配置
@ConditionalOnMissingBean(value = AccountService.class)

//当两个条件都不存在时，执行配置
@ConditionalOnMissingBean(name = "buildUserService2",value = AccountService.class)

//无视类型UserService的存在，执行配置
@ConditionalOnMissingBean(name = "buildUserService",value = AccountService.class,ignored = UserService.class)
```

### 2.ConditionalOnBean

该注解与上面的恰好相反

```java
//两个注解都存在，那么以missing为主
@ConditionalOnBean
@ConditionalOnMissingBean

//并集关系
@ConditionalOnBean(UserService.class)
@ConditionalOnMissingBean(name = "buildUserService2",value = AccountService.class)
```

![image-20210325094028706](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210325094028706.png)

## Springboot Web开发

### 静态资源

1.在springboot，我们可以使用以下方式处理静态资源

- webjars                                   localhost:8080/webjars/
- public ,static,/**,resources      localhost:8080/

### 整合freemarker

#### 依赖

```xml
   <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-freemarker</artifactId>
        </dependency>
```

#### yaml配置

```yaml
spring:
  freemarker:
    prefix: classpath:/templates/
    suffix: .ftl
```

#### 在templates下建xxx.ftl

```jsp
<#if flag>
    ${title}
    <#else >
    hehe
</#if>

```

#### 基本使用

##### 使用map传值

```java
@GetMapping("free1")
    public String free1(Map<String,Object> map){
        map.put("title","Fantasy");
        map.put("flag",false);
        return "free1";
    }
```

##### 使用request传值

```java
@GetMapping("free2")
    public String free2(HttpServletRequest request){
        request.setAttribute("flag",true);
        request.setAttribute("title","今晚打老虎");
        return "free1";
    }
```

### 整合thymeleaf

#### 导入

```
  <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
```

#### 在yaml下配置

修改模板路径，或者其他属性可自行探究，

```yaml
spring:
  thymeleaf:
    prefix: classpath:/thymeleaf/
```

#### 注意事项

- 一但导入thymeleaf，那么视图解析器就会优先跳转到thymeleaf的模板路径，不会跳到static下的同名路径，如果需要跳转static下，请使用**"forward:/index/thy1.html"**，注意路径前的/不能少。还有thymeleaf的视图路径不要加/,因为你已经在yaml配置文件里加有/了。

#### 基本使用

##### 后台传值

```java
  @GetMapping("/thy2")
    public String thy2(Map<String,Object> map){
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
           users.add(new User("jimmy"+i,i));
        }
        map.put("users",users);
        return "/t1/thy2";
    }
```

##### 使用前在html标签加入以下

```html
<html lang="en" xmlns:th="http://www.thymeleaf.org">
```

##### 1.集合遍历

```html
#遍历集合以及数组
#下面属性i的内容：{index = 0, count = 1, size = 5, current = User{name='jimmy0', age=0}}
#${}里面可随意拼接字符串
<div th:each="user,i:${users}">
    <div th:text="${user.name +'今晚打老虎'+ i.count}"></div>
    <div th:text="${user.age}"></div>
</div>

#获取数组某个下标的元素直接遍历
<div th:text="${users.get(1).name}"></div>
<div th:text="${users.get(1).age}"></div>
#map
<div th:text="${userMap.get('ccc')}"></div>
```

##### 2.if判断

```html
<div th:if="${i.index!=3}">
        <div th:text="${user.name +'今晚打老虎'+ i.count}"></div>
        <div th:text="${'小明今年'+user.age+'岁了'}"></div>
</div>

<div th:if="${i.index==3}">
        <div th:text="${user.name +'今晚打老虎'+ i.count}"></div>
        <div th:text="${'小明今年'+user.age+'岁了'}"></div>
</div>

#三目运算符
<div th:text="${user.age==3?2:3}"></div>
```

### 后台使用Date接受前台传的时间

要么直接用string来接受，要么加入以下配置

```java
@Configuration
public class IWebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToDate());
    }
    static class StringToDate implements Converter<String, Date> {
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
}
```

### 404及500页面处理

```java
//或者直接在static目录下创建error文件夹添加404等页面，下面这种方式post请求会提示找不到
@Configuration
public class ErrorPageConfig implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        /*1、按错误的类型显示错误的网页*/
        /*错误类型为404，找不到网页的，默认显示404.html网页*/
        ErrorPage e404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404.html");
        /*错误类型为500，表示服务器响应错误，默认显示500.html网页*/
//        ErrorPage e500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/static/error/index.jsp");
//        ErrorPage e400 = new ErrorPage(HttpStatus.BAD_REQUEST, "/static/error/index.jsp");
        registry.addErrorPages(e404);
    }
}

```

## 跳转首页的100种方法

#### 1.拦截器

```java
//使用拦截器
public class IndexInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("/".equals(request.getRequestURI())){
            request.getRequestDispatcher("/index").forward(request,response);
            return false;
        }
        return true;
    }
}
//注册拦截器，这里要在springboot配置文件同路径的包名下创建
@Configuration
public class IWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new IndexInterceptor())
                .addPathPatterns("/**");
    }
}
//使用forward跳转，static下的静态页面
@GetMapping("/index")
    public String toMain(){
        return "forward:/index.html";
}
```

#### 2.web配置文件添加视图控制器

```java
//这里要在springboot配置文件同路径的包名下创建
@Configuration
public class IWebConfig implements WebMvcConfigurer {   
    //或者直接配置/跳转到指定的地方
     @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("main");
    }
}
```

#### 3.controller直接跳转

```java
@Controller
public class IndexController {
    @RequestMapping({"/","index"})
    public String index(){
        return "index";
    }
}
```

#### 

## 整合JdbcTemplate

### 依赖

```xml
 <!--数据库连接，这里是5.1.49版本，在父工程自己家版本控制-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <!--jdbcTemplate-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
```

### Yaml配置

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/base
    username: root
    password: 1998
```

### 测试代码

```java
 @Test
//插入操作
    public void test2(){
        int update = jdbcTemplate.update("insert into user(username, password, phone, address) " +
                "values (?,?,?,?)","jimmy","123","10086","beijing");
        System.out.println(update);
    }
//查询所有
  @Test
    public void test3(){
        List<User> userList = jdbcTemplate.query("select * from user", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setPhone(resultSet.getString("phone"));
                user.setAddress(resultSet.getString("address"));
                return user;
            }
        });
        for (User user1 : userList) {
            System.out.println(user1);
        }
    }
//查询单个
 @Test
    public void test4(){
        System.out.println(jdbcTemplate.query("select * from user where id = ?", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setPhone(resultSet.getString("phone"));
                user.setAddress(resultSet.getString("address"));
                return user;
            }
        }, 100));
    }
```

## 整合Druid

### 1.普通整合

#### 依赖

```xml
<!--数据库连接-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId><!--版本1.1.23-->
        </dependency>
```

#### druid.properties

```properties
druid.url=jdbc:mysql://localhost:3306/base?characterEncoding=UTF8
druid.driverClassName=com.mysql.jdbc.Driver
druid.username=root
druid.password=1998
druid.initialSize=10
druid.maxActive=30
druid.maxWait=2000
```

#### configuration

```java

@PropertySource("classpath:druid.properties")
@Configuration
public class DruidDataSourceConfig {
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

    @Bean
    public DruidDataSource buildDruidDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(this.driverClassName);
        druidDataSource.setUrl(this.url);
        druidDataSource.setUsername(this.username);
        druidDataSource.setPassword(this.password);
        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMaxActive(this.maxActive);
        druidDataSource.setMaxWait(this.maxWait);
        return druidDataSource;
    }
}

```

#### 测试

```java
public void test2() throws SQLException {
        DruidPooledConnection connection = druidDataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement("select * from user where id = ?");
        ps.setInt(1,100);
        ps.execute();
        ResultSet resultSet = ps.getResultSet();
        ResultSetMetaData metaData = resultSet.getMetaData();
        while (resultSet.next()) {
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                System.out.println(resultSet.getString(i));
            }
        }
    }
```

### springboot整合+log4j2

#### 依赖

```xml
  <!--数据库连接 5.1.49-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <!--spring-jdbc-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <!--druid 1.2.5-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
        </dependency>
        <!--log4j2-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-log4j2</artifactId>
        </dependency>
```

#### yaml配置

```yaml
spring:
  freemarker:
    suffix: .ftl
  thymeleaf:
    prefix: classpath:/thymeleaf/
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/base?characterEncoding=UTF8
    username: root
    password: 1998
    druid:
      initial-size: 10
      max-active: 100
      min-idle: 10
      max-wait: 60000
      pool-prepared-statements: true
      max-pool-prepared-statement-per-connection-size: 20
      time-between-eviction-runs-millis: 60000
      min-evictable-idle-time-millis: 30000
      max-evictable-idle-time-millis: 60000
      validation-query: SELECT 1 FROM DUAL
      test-on-borrow: false
      test-on-return: false
      test-while-idle: true
      connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
      filters: stat,wall,log4j2
      stat-view-servlet:
        enabled: true
        url-pattern: /druid/*
    type: com.alibaba.druid.pool.DruidDataSource  
#去除spring自动生成配置的报告
logging:
  level:
    org:
      springframework:
        boot:
          autoconfigure:
            logging: info
```

#### druid监控配置

配置完后访问**localhost:81/druid**即可查看监控页面

```java
@Configuration
public class DruidConfig {
//    @Bean
//    @ConfigurationProperties("spring.datasource")
//    public DataSource buildDruidDataSource(){
//        return new DruidDataSource();
//    }
    //后台监控
    @Bean
    public ServletRegistrationBean<StatViewServlet> iWatch(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        Map<String,String> map = new HashMap<>();
        map.put("loginUsername","admin");
        map.put("loginPassword","123456");
        map.put("allow","");       //不写 或者为null 默认允许所有
        bean.setInitParameters(map);
        return  bean;
    }
}
```

#### log4j2配置

##### 依赖

```xml
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-log4j2</artifactId>
        </dependency>
<!-- 排除logging -->
         <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-logging</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
```

##### xml

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
        <root level="info">
            <appender-ref ref="Console"/>
        </root>
    </loggers>

</configuration>
```

## 整合Mybatis

### 依赖

```xml
  <!--mybatis2.1.4-->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
        </dependency>
```

### Yaml配置

```yaml
mybatis:
  type-aliases-package: com.ex.entity
  mapper-locations: classpath:mybatis/mapper/*.xml
  configuration:
    local-cache-scope: statement
    cache-enabled: false
```

### mapper

mapper.xml配置照旧

```java
@Mapper//可以在springboot启动类上架MapperScan直接扫描包
public interface UserMapper {
    List<User> queryAll();
}

```

## 整合mybatis-Plus

### 依赖

```xml
<!--mybatis-plus3.4.2-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
        </dependency>
<!--如果你要使用pagehelper，不用不要导入-->
<dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper</artifactId>
            <version>5.1.2</version>
        </dependency>
```

### yaml配置

```xml
mybatis-plus:
  mapper-locations: classpath:mybatis/mapper/*.xml
  configuration:
    cache-enabled: false
  type-aliases-package: com.ex.entity
```

mapper配置与普通mybatis配置一致，plus的功能看官网

### 分页设置

```java
@Configuration
@MapperScan("com.ex.mapper")
public class MybatisPlusConfig {
    @Bean//一定要放在前面
    public Interceptor buildMyInterceptor(){
        return new SQLExtractLog();
    }
    
     //这个和下面那个只能存在一个
    @Bean  
    public Interceptor buildPageHelper(){
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect","mysql");
        properties.setProperty("reasonable","true");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }
    
  //用这个屏蔽上面那个  
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        return interceptor;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            configuration.setCacheEnabled(false);
            configuration.setLocalCacheScope(LocalCacheScope.STATEMENT);
        };
    }
}

```

## 整合Redis

在springboot2.x之后，原来使用的jedis被替换成了lettuce

Jedis:采用的直连，多个线程操作的话，是不安全的，如果想要避免不安全，使用Jedis pool连接池！BIO

lettuce：采用netty，实例可以再多个线程中进行共享，不存在线程不安全的情况！可以减少线程数量！NIO

### 依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

### yaml配置

```yaml
  redis:
    host: 47.112.181.157
    port: 6379
    password: 123456
```

### 自定义RedisTemplate

```java
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance ,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();

        return template;
    }
}
```

### 基本使用                              

```java
//判断key是否存在
redisTemplate.countExistingKeys(Arrays.asList("mykey", "k1")//如果有一个key不存在则为0，都存在为1
redisTemplate.hasKey("mykey")//存在为true，否则false    
//操作string
redisTemplate.opsForValue
//操作hash
redisTemplate.opsForHash                                
//操作zset
  Set<ZSetOperations.TypedTuple<Object>> set = new HashSet<>(Arrays.asList(
                new DefaultTypedTuple<>("打老虎", 60d),
                new DefaultTypedTuple<>("打飞机", 70d),
                new DefaultTypedTuple<>("打地鼠", 90d)
                ));
        redisTemplate.opsForZSet().add("zz",set);

        Set<ZSetOperations.TypedTuple<Object>> zz = redisTemplate.opsForZSet().rangeWithScores("zz", 0, -1);
        for (ZSetOperations.TypedTuple<Object> typedTuple : zz) {
            System.out.println(typedTuple.getValue()+","+typedTuple.getScore());
        }
 
                             
```

### 使用jackson序列化

```java
 public void test2() throws JsonProcessingException {
        User user = new User();
        user.setUsername("kim");
        user.setPassword("123");
        ObjectMapper objectMapper = new ObjectMapper();
        String s = new ObjectMapper().writeValueAsString(user);
        redisTemplate.opsForValue().set("user",s);
        String s1 = (String) redisTemplate.opsForValue().get("user");
        User user1 = objectMapper.readValue(s1, User.class);
        System.out.println(user1);
    }
```

### 哨兵模式配置

代码无需改变，只改配置

```yaml
redis:
    host: 47.112.181.157
    port: 6381
    password: 123456
    sentinel:
      master: mymaster
      nodes: 47.112.181.157:26379,47.112.181.157:26380,47.112.181.157:26381
      password: 123456
    timeout: 5000
```

## 整合Security

### 依赖

```xml
<!--springboot-security-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
```

### 简介

Spring Security是针对spring项目的安全框架，也是spring boot底层安全模块默认的技术选型，他可以实现强大的Web安全控制，对于安全控制，我们仅需要引入spring-boot-starter-security模块，进行少量的配置，即可实现强大的安全管理！

几个常用的类：

- WebSecurityConfigurerAdapter：自定义Security策略
- AuthenticManagerBuilder：自定义认证策略
- @EnableWebSecurity：开启WebSecurity模式

spring Security的两个主要目标是认证和授权（访问控制）

认证：Authentication

授权：Authorization

这个概念是通用的，而不是只在spring Security中存在

### 加密

```java
@Test
    public void test1(){
        PasswordEncoder pw = new BCryptPasswordEncoder();
        String encode = pw.encode("123");
        log.info("加密后：{}",encode);
        boolean matches = pw.matches("123", encode);
        log.info("是否匹配：{}",matches?"是":"否");
    }
```

### 基本配置

#### 1.无脑配置

```java
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //授权
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/user/l1/**").hasRole("v1")
                .antMatchers("/user/l2/**").hasRole("v2")
                .antMatchers("/user/l3/**").hasRole("v3");
        //没有登录就会跳转到登录页面
        http.formLogin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //应从数据库中读取添加
        auth.inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder()).withUser("jimmy").password(new BCryptPasswordEncoder().encode("123")).roles("v1","v2")
                .and()
                .passwordEncoder(new BCryptPasswordEncoder()).withUser("root").password(new BCryptPasswordEncoder().encode("123")).roles("v1","v2","v3")
                .and()
                .passwordEncoder(new BCryptPasswordEncoder()).withUser("laowang").password(new BCryptPasswordEncoder().encode("123")).roles("v1");

    }
}
```





