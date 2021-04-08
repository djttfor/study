

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

### 跳转首页的100种方法

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

### 注册原生Filter

#### 1.编写Filter

```java
@Slf4j
@WebFilter(urlPatterns = "/*",filterName = "iFirstFilter")
public class FirstFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("iFirstFilter被初始化了");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("iFirstFilter执行了");
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        log.info("iFirstFilter被销毁了");
    }
}
```

#### 2.注册Filter

1. ##### 方式1：在启动类使用注解扫描

   ```java
   @ServletComponentScan("com.ex.filter")
   @SpringBootApplication
   public class SSS {
       public static void main(String[] args) {
           SpringApplication.run(SSS.class,args);
       }
   }
   ```

2. ##### 方式2：使用@Bean注册，使用该方法时Filter类不用写@WebFilter注解

   ```java
   @Configuration
   public class FilterConfig {
       @Bean
       public FilterRegistrationBean<ThirdFilter> setFilter(){
           FilterRegistrationBean<ThirdFilter> filterBean = new FilterRegistrationBean<>();
           filterBean.setFilter(new ThirdFilter());
           filterBean.setName("iThirdFilter");
           filterBean.addUrlPatterns("/*");
           return filterBean;
       }
   }
   
   ```

#### 3.@order（int）

表示注册bean的属性，值越小越优先被注册，使用方式2注册时才会提现出来，方式1是按java文件顺序排序

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
    //一定要放在前面
    @Bean
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
    @Resource(name = "iUserDetailServiceImpl")
    UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //配置权限控制
        http.authorizeRequests()
                //设置首页、静态资源、错误页面等过滤
                .antMatchers("/","/css/**","/js/**","/error/**").permitAll()
                .antMatchers("/user/login/**").permitAll()
                //设置拥有角色
                .antMatchers("/user/l1/**").hasRole("v1")
                .antMatchers("/user/l2/**").hasRole("v2")
                .antMatchers("/user/l3/**").hasRole("v3")
                //所有的请求必须认证才能访问，必须登录
                .anyRequest().authenticated();

        //自定义登录页面
        http.formLogin().loginPage("/user/login/page")
        //登录处理页面
        .loginProcessingUrl("/user/login/process")
        //登录成功的页面
        .successForwardUrl("/index")
        //登录成功的页面
        .failureForwardUrl("/user/login/fail");


        //相当于关闭防火墙
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //在内存中存储密码的加密方式
//        auth.inMemoryAuthentication()
//                .passwordEncoder(new BCryptPasswordEncoder()).withUser("jimmy").password(new BCryptPasswordEncoder().encode("123")).roles("v1","v2")
//                .and()
//                .passwordEncoder(new BCryptPasswordEncoder()).withUser("root").password(new BCryptPasswordEncoder().encode("123")).roles("v1","v2","v3")
//                .and()
//                .passwordEncoder(new BCryptPasswordEncoder()).withUser("laowang").password(new BCryptPasswordEncoder().encode("123")).roles("v1");

        //在数据库中存取密码的加密方式
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}

```

#### 2.轻松配置

```java
 @Override
    protected void configure(HttpSecurity http) throws Exception {
        //授权
        http.authorizeRequests().antMatchers("/","/css/**","/js/**","/error/**").permitAll()
                .antMatchers("/user/login/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/user/login/page")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write("{\"status\":\"ok\",\"msg\":\"登录成功\"}");
                        out.flush();
                        out.close();
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write("{\"status\":\"error\",\"msg\":\"登录失败\"}");
                        out.flush();
                        out.close();
                    }
                })
                .loginProcessingUrl("/user/login/ass")
                .usernameParameter("username")
                .passwordParameter("password").permitAll()
                .and().csrf().disable()
                .logout().logoutUrl("/exit").permitAll();

    }
```



### 原理解析

springSecurity本质上是一个过滤器，拦截路径为/*,他自身又封装了11种拦截器，如下

具体逻辑在这个org.springframework.security.web.FilterChainProxy#doFilterInternal（）里面

![image-20210329155943914](springboot.assets/image-20210329155943914.png)

springSecurity未登录时的基本执行流程：逐次通过这11个拦截器，在最后一个拦截器判断此次请求是否拥有访问某个资源的权限，如果没有就抛出（AccessDeniedException）拒绝访问异常，然后通过处理该异常，发送开始身份认证：先把本次请求保存到Session中，然后获取登录页面的地址（URL），然后直接重定向到该地址

### 自定义登录校验

流程：Security框架把用户名给你，让你去查数据库有没有这个用户的相关信息，如果有查询出来对比账号密码，密码一定要加密，详见无脑配置，如果没有查到就抛出UsernameNotFoundException，具体操作如下

```java
@Service("iUserDetailServiceImpl")
public class IUserDetailServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (!"root".equals(s)) {
            throw new UsernameNotFoundException("用户名不是root");
        }
        return new User("root",new BCryptPasswordEncoder().encode("123"),
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_v1,ROLE_v2"));//指定权限的时候一定要加ROLE_作为前缀
    }
}
```

### 自定义登录成功跳转界面

登录失败跳转页面类似（AuthenticationFailureHandler）

```java
public class ISuccessHandler implements AuthenticationSuccessHandler {

    private final String forwardUrl;

    public ISuccessHandler(String forwardUrl) {
        this.forwardUrl = forwardUrl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.sendRedirect(forwardUrl);
    }
}
//使用
  protected void configure(HttpSecurity http) throws Exception {
        //自定义登录页面
        http.formLogin().loginPage("/user/login/page")
        //登录处理页面
        .loginProcessingUrl("/user/login/process")
        //登录成功的页面
        .successHandler(new ISuccessHandler("http://www.baidu.com"))
  }
```

### 权限配置

#### 1.antMatcher（）

参数是可变参数，每个参数是一个ant表达式，用于匹配URL规则

规则如下

- ？ 匹配一个字符
- ‘*’   匹配0个或多个字符
- **   匹配0个或多个目录

```java
protected void configure(HttpSecurity http) throws Exception {
        //权限控制
        http.authorizeRequests()
                .antMatchers("/user/l1/**").hasRole("v1")//匹配多个目录
                .antMatchers("/user/l2/*.js").hasRole("v2")//匹配多个js文件
    }
```

#### 2.hasRole

配置角色，给用户授权时要加上**ROLE_**的前缀，表示拥有此角色才能访问此路径

注意事项：

如果是hasAnyRole（roles）配置了多个角色，只要有其中一个角色就能访问改路径；

如果是你还配置了hasAuthority，如果用户拥有这个权限，那么用户也可以访问该路径；

总之，他们之间是**或（||）**的关系。

```java

protected void configure(HttpSecurity http) throws Exception {
        //权限控制
        http.authorizeRequests()
                .antMatchers("/user/l1/**").hasRole("v1")//匹配多个目录
                .antMatchers("/user/l2/*.js").hasRole("v2")//匹配多个js文件
    }

public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (!"root".equals(s)) {
            throw new UsernameNotFoundException("用户名不是root");
        }
        return new User("root",new BCryptPasswordEncoder().encode("123"),
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_v1,ROLE_v2"));//授予角色
    }
```

#### 3.hasAuthority

配置权限，拥有此权限才能访问此路径

注意事项：

如果你使用hasAnyAuthority或者hasAuthority给一个路径配置了多个权限，那么用户只需要其中一个权限就能访问该路径。

```java
protected void configure(HttpSecurity http) throws Exception {
        //权限控制
        http.authorizeRequests()
                .antMatchers("/user/l1/**").hasRole("v1")//匹配多个目录
                .antMatchers("/user/l1/**").hasAuthority("v1")
    }

public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (!"root".equals(s)) {
            throw new UsernameNotFoundException("用户名不是root");
        }
        return new User("root",new BCryptPasswordEncoder().encode("123"),
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_v1,v1"));//授予权限
    }
```

#### 4.hasIpAddress

需要在地址栏指定一个特定的IP才能访问

```
antMatchers("/user/l1/**").hasIpAddress("127.0.0.1")
```

#### 5.access

2-4都是通过access来进行调用的，详见官网，access可以自己定制权限控制，不过需注意anyRequest是先于登录验证的，而且需注意权限控制时路径的匹配问题，如下

```java
        //授权
        http.authorizeRequests(auth ->{
            //设置首页、静态资源、错误页面等过滤
            auth.antMatchers("/","/css/**","/js/**","/error/**").permitAll()
                    .antMatchers("/user/login/**").permitAll()

                    .antMatchers("/user/l1/**").hasRole("v1")//如果这个匹配了，那么不会执行下面了

                    .anyRequest().access("@IAuthControl.hasPermission(request,authentication)");
            //所有的请求必须认证才能访问，必须登录
        });
       
```

access自定义权限控制

```java
@Component
public class IAuthControl{
    public Boolean hasPermission(HttpServletRequest request,Authentication authentication) {
        String uri = request.getRequestURI();
        log.info("当前uri：{}",uri);
        //获取userDetail
        Object principal = authentication.getPrincipal();
        //判断用户是否已经登录，不是匿名用户      
        if(principal instanceof UserDetails){
            //获取该用户拥有的权限
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();         
            //判断该用户是否拥有此路径权限，这里仅仅模拟
            //真正的场景是要到数据库查询权限进行验证
            return authorities.contains(new SimpleGrantedAuthority("v1"));
        }
        //拒绝访问
        return false;
    }
}
```

### 基于注解的权限控制

启用注解支持

```java
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
```

#### 1.@Secured

写在Controller方法上，如下

```java
 /*
 相当于.antMatchers("/user/l1/**").hasRole("v1","v2"),
 可以与java配置同时使用，
 如果配置了.antMatchers("/user/l1/**").hasRole("v1")与@Secured({"ROLE_v2"})
 相当于要过两道坎，如果用户的权限只有v1,那么会被注解那里拦下，如果是v2，会被java配置拦下，所有要同时有v1,v2才行
 */
@Secured({"ROLE_v1","ROLE_v2"})
    @RequestMapping("/l1/{l}")
    public String l1(@PathVariable("l")String l){
        return "L1/"+l;
    }
```

#### 2.@PreAuthorize

表示访问方法或类在执行之前先判断权限，大多数情况下都是使用这个注解，注解的参数和access（）方法取值相同，都是权限表达式

与@Secured的区别是没有区别

```java
@PreAuthorize("hasAnyRole('v2')")
@RequestMapping("/l1/{l}")
public String l1(@PathVariable("l")String l){
    log.info("当前访问的是：{}","L1/"+l);
    return "L1/"+l;
}
```

#### 3.@PostAuthorize

表示方法或类执行结束后判断权限，此注解很少被使用到。

### Remember-me

需要依赖jdbc

```xml
 <!--数据库连接-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <!--spring-jdbc-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
```

配置

```java
 //记住我
        http.rememberMe()
                .tokenRepository(persistentTokenRepository)
                .tokenValiditySeconds(60)
                .userDetailsService(userDetailsService);
                
    @Bean
    public PersistentTokenRepository buildPersistentTokenRepository(DataSource dataSource){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);

        //自动建表,每次启动都回去建表，所以第二次要注释掉
       // jdbcTokenRepository.setCreateTableOnStartup(true);

        return jdbcTokenRepository;
    }
```

```html
<div id="rem"><label for="rm">自动登录</label><input id="rm" name="remember-me" type="checkbox"></div>
```

### 登出

配置

```java
http.logout()
                .logoutUrl("/lu")//默认是/logout
                .logoutSuccessUrl("/");//登出后跳转界面
```

```html
<div style="float: right"><a href="/lu">登出</a></div>
```

### CSRF

#### 什么是CSRF

- CSRF（Cross site request forgery）跨站请求伪造，也被称为”OneClick Attack“或者 Session Riding，通过伪造用户请求访问受信任站点的非法请求访问
- 跨域：只要协议、IP、端口中任一不同皆为跨域
- 客户端与服务端进行交互时，由于http协议是无状态协议，所有引入了cookie进行记录客户端身份，在cookie中会存放session id用来识别客户端身份的。在跨域的情况下，Session id可能被第三方恶意劫持，通过这个Session id向客户端发起请求时，服务端会认为这个请求是合法的，可能发生很多意想不到的事情。

#### Spring Security中的CSRF

- 从Spring Security4开始CSRF防护默认开启，默认会拦截请求，进行CSRF处理，CSRF为了保证不是第三方网站访问，要求访问时携带参数名为_csrf值为token（token在服务端产生）的内容，如果token和服务端的token匹配成功，则正常访问。

往后台发post请求时，带上这个参数

```html
<input type="hidden" name="_csrf" th:value="${_csrf.token}" th:if="${_csrf}">
```

### JJWT

介绍详见JWT介绍.md

#### 依赖

```xml
  <!--JWT依赖-->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt</artifactId>
            <version>0.9.1</version>
        </dependency>
```

#### 生成token

```java
 @Test
    public void test1(){
        JwtBuilder jwtBuilder = Jwts.builder()
                //唯一id
                .setId("666")
                //接受的用户
                .setSubject("Van")
                //签发时间
                .setIssuedAt(new Date())
                //签名及密钥
                .signWith(SignatureAlgorithm.HS256,"The deep dark fantasy");

        String token = jwtBuilder.compact();
        log.info("token:{}",token);
        log.info("-------------令牌解析----------------");
        String[] split = token.split("\\.");
        log.info("第一部分：{}", new String(Base64Codec.BASE64.decode(split[0])));
        log.info("第二部分：{}", new String(Base64Codec.BASE64.decode(split[1])));
        log.info("第三部分：{}", new String(Base64Codec.BASE64.decode(split[2])));
    }
```

#### 解析token

```java
 @Test
    public void test2(){
        String s = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiJWYW4iLCJpYXQiOjE2MTcxNzk3MTZ9.dkRgtVuaeEjyBqc5gtt5Teg98_Cv-mUsFSCNQG8EXh8";
        Claims claims = (Claims) Jwts.parser().setSigningKey("The deep dark fantasy").parse(s).getBody();
        log.info("id:{}",claims.getId());
        log.info("创建时间:{}",claims.getIssuedAt());
        log.info("用户:{}",claims.getSubject());
    }
```

#### token过期校验

过期的token，解析会抛出异常

```java
 @Test
    public void test1(){
        long date = System.currentTimeMillis();
        long exp = date + 60 * 1000;
        JwtBuilder jwtBuilder = Jwts.builder()
                //唯一id
                .setId("666")
                //接受的用户
                .setSubject("Vans")
                //签发时间
                .setIssuedAt(new Date())
                //设置失效时间
                .setExpiration(new Date(exp))
                //签名及密钥
                .signWith(SignatureAlgorithm.HS256,"The deep dark fantasy");

        String token = jwtBuilder.compact();
        log.info("token:{}",token);
        log.info("-------------令牌解析----------------");
        String[] split = token.split("\\.");
        log.info("第一部分：{}", new String(Base64Codec.BASE64.decode(split[0])));
        log.info("第二部分：{}", new String(Base64Codec.BASE64.decode(split[1])));
        log.info("第三部分：{}", new String(Base64Codec.BASE64.decode(split[2])));
    }
```

#### 自定义Claims

```java
.claim("name","jimmy")
.claim("ass","hole")//自定义
    
log.info("name:{}",claims.get("name"));
log.info("ass:{}",claims.get("ass"));//获取
```

## 整合Shiro

### 依赖

```xml
<!-- shiro-->
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-spring</artifactId>
    <version>1.7.1</version>
</dependency>
```

### 配置

```java
//shiro拦截器，拦截请求进行认证
@Bean
public ShiroFilterFactoryBean buildShiroFilterFactory(WebSecurityManager defaultWebSecurityManager){
    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
    /*
    shiro的内置过滤器
    anon：无需认证可以访问
    authc：必须认证了才能访问
    user：必须拥有记住我功能才能使用
    perms：拥有对某个资源的权限才能访问
    role：拥有某个角色才能访问
     */
    Map<String,String> filterMap = new LinkedHashMap<>();

    //设置主页不需要认证，需设在前面
    filterMap.put("/","anon");
    //设置所有路径都必须要经过认证
    filterMap.put("/*","authc");
    filterMap.put("/user/login/**","anon");

    filterMap.put("/user/l1/**","perms[l1]");
    filterMap.put("/user/l2/**","perms[l2]");
    filterMap.put("/user/l3/**","perms[l3]");

    //登出
  //  filterMap.put("/user/logout","logout");

    //添加验证逻辑
    shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

    //设置登录界面
    shiroFilterFactoryBean.setLoginUrl("/user/login/page");

    //设没有权限时访问的页面
    shiroFilterFactoryBean.setUnauthorizedUrl("/user/unauth");

    return shiroFilterFactoryBean;
}
//安全管理器，配置remember-me、用户认证、权限校验等等。
 //defaultWebSecurityManager
    @Bean
    public WebSecurityManager buildDWSM(UserRealm userRealm,CookieRememberMeManager cookieRememberMeManager){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(userRealm);
        defaultWebSecurityManager.setRememberMeManager(cookieRememberMeManager);
        return defaultWebSecurityManager;
    }
//remember-me功能
    @Bean
    public CookieRememberMeManager buildCookieRememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        SimpleCookie cookie = new SimpleCookie("iRememberMe");
        cookie.setMaxAge(10*60);
        cookieRememberMeManager.setCookie(cookie);
        return cookieRememberMeManager;
    }
//用户登录，权限认证逻辑
    //自定义Realm
    @Bean
    public UserRealm buildUserRealm(){
        return new UserRealm();
    }
```

### 用户登录controller逻辑

```java
  @PostMapping("/login/process")
    @ResponseBody
    public Message loginProcess(User user,boolean rememberMe){
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            return new Message(1,"请输入用户名和密码！");
        }

        //用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                user.getUsername(),
                user.getPassword()
        );
        //记住我
        if(rememberMe){
            usernamePasswordToken.setRememberMe(true);
        }
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
        } catch (UnknownAccountException e) {
            log.error("用户名不存在！", e);
            return new Message(1,"用户名不存在！");
        } catch (AuthenticationException e) {
            log.error("账号或密码错误！", e);
            return new Message(1,"账号或密码错误！");
        } catch (AuthorizationException e) {
            log.error("没有权限！", e);
            return new Message(1,"没有权限");
        }
        return new Message(2,"login success");
    }
```

### 用户登出

#### 方式1

直接在shiro过滤器配置里面添加以下配置，该路径可以是不存在的

```java
filterMap.put("/user/logout","logout");
```

#### 方式2

不在配置里面配置，自定义路径在controller里面使用subject登出

```java
  @RequestMapping("/logout")
    @ResponseBody
    public Message logout(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.getPrincipal()!=null){
            subject.logout();
            return new Message(3,"登出成功");
        }
        return new Message(3,"请先登录");
    }
```

## Swagger2

在团队开发中，一个好的 API 文档不但可以减少大量的沟通成本，还可以帮助一位新人快速上手业务。传统的做法是由开发人员创建一份 RESTful API 文档来记录所有的接口细节，并在程序员之间代代相传。

这种做法存在以下几个问题：

- API 接口众多，细节复杂，需要考虑不同的HTTP请求类型、HTTP头部信息、HTTP请求内容等，想要高质量的完成这份文档需要耗费大量的精力；
- 难以维护。随着需求的变更和项目的优化、推进，接口的细节在不断地演变，接口描述文档也需要同步修订，可是文档和代码处于两个不同的媒介，除非有严格的管理机制，否则很容易出现文档、接口不一致的情况

**Swagger2** 的出现就是为了从根本上解决上述问题。它作为一个规范和完整的框架，可以用于生成、描述、调用和可视化 RESTful 风格的 Web 服务：

1. 接口文档在线自动生成，文档随接口变动实时更新，节省维护成本
2. 支持在线接口测试，不依赖第三方工具

### 依赖

```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.9.2</version>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
</dependency>
```

### 配置

访问localhost/swagger-ui.html，即可看见文档

```java
@Configuration
@EnableSwagger2
public class Swagger2Config {
    //api接口包扫描路径
    public static final String SWAGGER_SCAN_BASE_PACKAGE = "com.ex.controller";

    public static final String VERSION = "1.0.0";

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(true)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ex.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Luis Site Swagger Restful API")
                .description("更多Spring Boot相关文章请关注：https://luischen.com/")
                .termsOfServiceUrl("https://luischen.com/")
                .contact(new Contact("jimmy","www.baidu.com","123@qq.com"))
                .version("1.0")
                .build();
    }
}

```

### 常用注解

@Api：修饰整个类，描述Controller的作用

@ApiOperation：描述一个类的一个方法，或者说一个接口

@ApiParam：单个参数描述

@ApiModel：用对象来接收参数

@ApiProperty：用对象接收参数时，描述对象的一个字段

@ApiResponse：HTTP响应其中1个描述

@ApiResponses：HTTP响应整体描述

@ApiIgnore：使用该注解忽略这个API

@ApiError ：发生错误返回的信息

@ApiImplicitParam：描述一个请求参数，可以配置参数的中文含义，还可以给参数设置默认值

@ApiImplicitParams：描述由多个 @ApiImplicitParam 注解的参数组成的请求参数列表