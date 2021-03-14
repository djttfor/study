### mybatisplus

#### 1.代码生成器

导入

```xml

```

代码,具体配置看mybatis-plus官网

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

### AOP

#### 1.xml方式

xml配置

```xml
<aop:config >
    <aop:aspect ref="timeRecordAdvice" >
        <aop:pointcut id="timeLog" expression="execution(* com.ex.smp.service.*.*(..))"/>
        <aop:before method="before" pointcut-ref="timeLog"/>
        <aop:after method="after" pointcut-ref="timeLog"/>
        <aop:after-returning method="end" pointcut-ref="timeLog"/>
        <aop:after-throwing method="throwing" pointcut-ref="timeLog" />
    </aop:aspect>
</aop:config>
 <!--事务通知-->
 <aop:config >
        <aop:advisor advice-ref="txAdvice" pointcut="execution(* com.ex.smp.service.*.*(..))"/>
    </aop:config>
    <tx:advice id="txAdvice" >
        <tx:attributes>
            <tx:method name="transfer2"  />
        </tx:attributes>
    </tx:advice>
```

切面类

```java
@Aspect //需声明EnableAspectJAutoProxy 注意:使用注解AfterReturning在After之前执行.建议使用around
@Component//xml方式留Component注解即可,其他注解去掉
public class TimeRecordAspectAdvice {
    private static final Logger log = LoggerFactory.getLogger(TimeRecordAdvice.class);
    private Long startTime;

    @Pointcut("execution(* com.ex.smp.service.*.*(..))")
    public void expression(){}

    @Before("expression()")
    public void before(){
        log.info("开始,当前时间为{}ms",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
        startTime = System.currentTimeMillis();
    }
    @After("expression()")
    public void after(){
        log.info("结束,当前时间为{}",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
        Long endTime = System.currentTimeMillis();
        log.info("总执行时间为{}ms", endTime -startTime);
    }
    @AfterReturning("expression()")
    public void end(){
        log.info("最终,当前时间为{}",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
    }

    @AfterThrowing("expression()")
    public void throwing(){
        log.info("异常,当前时间为{}",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
    }

    @Around("expression()")
    public Object around(ProceedingJoinPoint point){
        Object[] args = point.getArgs();
        try {
            log.info("前");
            Object result = point.proceed(args);
            log.info("后");
            return result;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            log.info("异常");
            throw new RuntimeException(throwable);
        }finally {
            log.info("最终");
        }
    }
}

```