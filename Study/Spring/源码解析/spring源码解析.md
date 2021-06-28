## BeanDefinition

### 什么是BeanDefinition

![image-20210407155405629](spring源码解析.assets/image-20210407155405629.png)

### BeanDefinition的重要属性

- beanClass<img src="spring源码解析.assets/image-20210407155637475.png" alt="image-20210407155637475" style="zoom: 150%;" />
- scope              <img src="spring源码解析.assets/image-20210407155700916.png" alt="image-20210407155700916" style="zoom:150%;" />
- isLazy<img src="spring源码解析.assets/image-20210407155714521.png" alt="image-20210407155714521" style="zoom:150%;" />
- dependsOn<img src="spring源码解析.assets/image-20210407155733238.png" alt="image-20210407155733238" style="zoom:150%;" />
- primary<img src="spring源码解析.assets/image-20210407155853050.png" alt="image-20210407155853050" style="zoom:150%;" />
- initMethodName<img src="spring源码解析.assets/image-20210407155933073.png" alt="image-20210407155933073" style="zoom:150%;" />

![image-20210407160025721](spring源码解析.assets/image-20210407160025721.png)

## BeanFactory

### 什么是BeanFactory

![image-20210407160134829](spring源码解析.assets/image-20210407160134829.png)

### 与BeanDefinition及Bean对象的关系

![image-20210407160354179](spring源码解析.assets/image-20210407160354179.png)

### BeanFactory的核心子接口和实现类

- ListableBeanFactory
- ConfigurableBeanFactory
- AutowireCapableBeanFactory
- AbstractBeanFactory
- DefaultListableBeanFactory

![image-20210407160819534](spring源码解析.assets/image-20210407160819534.png)

## Bean生命周期

### 什么是Bean生命周期

![image-20210407161156480](spring源码解析.assets/image-20210407161156480.png)

### 生命周期

![image-20210407161405717](spring源码解析.assets/image-20210407161405717.png)

## @Autowired

### 概念

![image-20210407161837234](spring源码解析.assets/image-20210407161837234.png)

### 查找规则

![image-20210407162007724](spring源码解析.assets/image-20210407162007724.png)

### 在构造方法上时

![image-20210407162202815](spring源码解析.assets/image-20210407162202815.png)

## @Resource

### 概念

![image-20210407162301726](spring源码解析.assets/image-20210407162301726.png)

### 查找规则

![image-20210407162435797](spring源码解析.assets/image-20210407162435797.png)

![image-20210407162442661](spring源码解析.assets/image-20210407162442661.png)

## @Value

### 概念

![image-20210407162547332](spring源码解析.assets/image-20210407162547332.png)

### 注入方式

![image-20210407162714082](spring源码解析.assets/image-20210407162714082.png)

![image-20210407162731950](spring源码解析.assets/image-20210407162731950.png)

![image-20210407162742317](spring源码解析.assets/image-20210407162742317.png)

## FactoryBean

### 概念

![image-20210407162822747](spring源码解析.assets/image-20210407162822747.png)

### 主要方法

![image-20210407162903208](spring源码解析.assets/image-20210407162903208.png)

### 示例

```java
@Component
public class AccountBean implements FactoryBean<Account> {
    /**
     * 获取Bean对象
     */
    @Override
    public Account getObject() throws Exception {
        return new Account();
    }

    /**
     * Bean类型
     */
    @Override
    public Class<?> getObjectType() {
        return Account.class;
    }

    /**
     * 是否单例
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
```

### 特殊点

上述代码，实际上对应了两个Bean对象

1. beanName为accountBean，bean对象为getObject方法所返回的Account对象
2. beanName为&accountBean，bean对象为AccountBean的实例对象

### 与BeanFactory的区别

![image-20210407164239372](spring源码解析.assets/image-20210407164239372.png)

### 应用

![image-20210407164306121](spring源码解析.assets/image-20210407164306121.png)

## ApplicationContext

### 概念

![image-20210407164502364](spring源码解析.assets/image-20210407164502364.png)

### 继承的接口

![image-20210407164527989](spring源码解析.assets/image-20210407164527989.png)

- EnvironmentCapable![image-20210407164735740](spring源码解析.assets/image-20210407164735740.png)
- ListableBeanFactory![image-20210407164744803](spring源码解析.assets/image-20210407164744803.png)
- HierarchicalBeanFactory![image-20210407164814308](spring源码解析.assets/image-20210407164814308.png)
- MessageSource![image-20210407164832976](spring源码解析.assets/image-20210407164832976.png)
- ApplicationEventPublisher![image-20210407164850525](spring源码解析.assets/image-20210407164850525.png)
- ResourcePatternResolver![image-20210407164905448](spring源码解析.assets/image-20210407164905448.png)

## BeanPostProcessor

### 概念

![image-20210407164957890](spring源码解析.assets/image-20210407164957890.png)

![image-20210407165134351](spring源码解析.assets/image-20210407165134351.png)

### 它的方法

![image-20210407165151511](spring源码解析.assets/image-20210407165151511.png)

```java
@Component
@Slf4j
public class IBeanPostProcessor implements BeanPostProcessor {
    //初始化后对bean做些什么，返回null表示什么都不做，继续生命周期
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }
    
//初始化前对bean做些什么
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("accountMapper".equals(beanName)) {
            log.info("这里有个accountMapper,快欺负它");
        }
        return bean;
    }

}
```

### 子接口

![image-20210407165227440](spring源码解析.assets/image-20210407165227440.png)

#### InstantiationAwareBeanPostProcessor

可以在bean实例化前后做出影响

```java
@Component
class InitBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    //实例化前，如果直接返回Object对象那么Spring会把它当成bean保存，不会进行接下来的生命周期步骤，直接执行了BeanPostProcessor的初始化后操作，如果返回null，那么继续生命周期
    @SneakyThrows
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) {
        if (Account.class.isAssignableFrom(beanClass)){
            Account account = (Account) beanClass.getConstructor().newInstance();
            account.setAccountName("aaa");
            account.setBalance(1000L);
            return account;
        }
        return null;
    }
    //实例化后，返回false，后面的postProcessProperties方法执行不了
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if(bean instanceof Account){
            log.info("account:{}",bean);
            return false;
        }
        return true;
    }
}
```

## InitializingBean

方法 afterPropertiesSet，相当于初始化方法

```java
@Override
    public void afterPropertiesSet() throws Exception {
        log.info("account:{}",this);
    }
```

## BeanDefinitionRegistryPostProcessor

方法postProcessBeanDefinitionRegistry

即可以自己注册beanDefinition，也可以判断其他beanDefinition存在已否来做相应的事

方法postProcessBeanFactory

用法跟前面的差不多，只是会在前面的方法执行完后执行

```java
@Component
public class ITest implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(IUser.class);
        beanDefinitionBuilder.addPropertyValue("name","张三");
        beanDefinitionBuilder.addPropertyValue("age",18);
        registry.registerBeanDefinition("iuser",beanDefinitionBuilder.getBeanDefinition());
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //如果找不到会抛出异常
        BeanDefinition iuser = beanFactory.getBeanDefinition("iuser");
        IUser iUser = new IUser();
        iUser.setName("李四");
        iUser.setAge(19);
        beanFactory.registerSingleton("iuser",iUser);
    }
}
```

## Mybatis-Spring @MapperScan原理

@MapperScan如何被spring解析的

![image-20210410190025194](spring源码解析.assets/image-20210410190025194.png)

MapperScannerConfigurer里面的骚操作（这里最后的步骤不完整）

![image-20210410200100814](spring源码解析.assets/image-20210410200100814.png)

## AOP

模仿AOP，在accountService执行前后增加时间打印并计算执行时间

```java
@Slf4j
@Component
public class InitBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof AccountService) {
            return Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(), (proxy, method, args) -> {
                ITimeLog iTimeLog = new ITimeLog();
                iTimeLog.before();
                Object result = method.invoke(bean, args);
                iTimeLog.after();
                return result;
            });
        }
        return null;
    }
}
```

## 

