### Mybatis的问题

#### 1.使用xml方式配置映射为什么可以同时使用注解?

xml解析的最后,会根据xml的名称空间获取对应接口的字节码去解析该接口里面所有的注解,
这就是能使用注解方式的原因,但需要注意,每一个方法只能使用一种方式配置,
不能同时使用两种方式同时配置,因为每一个方法或标签解析成mappedStatement后,都会以类名称空间.Id(注解是全限定类名.方法名)为key,
存入一个hashMap中,存入hashMap之前会判断是否已经存在该Key,存在就抛出异常. 
如果反过来,只使用注解配置,是不能使用xml中的映射的,因为根本就不会去解析xml文件.

#### 2.从源码的角度分析#{}与${}的区别

含有${}会被判断为动态语句,解析的时候保持不变.
含有#{}在解析的时候会被替换为?

#### 3.mapper方法多个参数传递问题

```java
private final SortedMap<Integer, String> names;//存放的是mapper接口方法参数@Param注解的值,key是顺序,如果注解@Param没写就会添加arg0(这后面的数字每多一个参数加一)
private final List<ParameterMapping> parameterMappings; //xml中的占位符名称,这个决定参数的取值
private final Object parameterObject;//参数都是从这里取
public static class ParamMap<V> extends HashMap<String, V> {//一般的所有参数会放在这里Map里面,
    private static final long serialVersionUID = -2212268410512043556L;
    @Override
    public V get(Object key) {
      if (!super.containsKey(key)) {
        throw new BindingException("Parameter '" + key + "' not found. Available parameters are " + keySet());
      }
      return super.get(key);
    }
  }
//注意:这个map保存参数的时候,每保存一个参数就会额外添加param1(这后面的数字每多一个参数加一)对应
//参数的取值:遍历parameterMappings,从paramMap中找,没找到就抛出BindingException
```

#### 4.一级缓存

一级缓存是无法关闭的,不想使用一级缓存有两种方法:
4.1 在mybatis配置文件中设置如下图,该配置设置后,一级缓存无效,二级缓存还能使用

```xml
<settings>
        <setting name="localCacheScope" value="STATEMENT"/>
</settings>
```

4.2 在mapper.xml中的select标签设置**flushCache**="true",在查询前就会把一二级缓存删掉(查询后mybatis马上就会保存缓存,第二次查询又会把刚刚保存的缓存删掉,就会有些性能问题)

#### 5.二级缓存

二级缓存默认是开启的,实体类一定要实现Serializable接口,而且一定要当前Session调用commit方法才能把数据添加到二级缓存中.

```java
public <E> List<E> query(MappedStatement ms, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler, CacheKey key, BoundSql boundSql)
    throws SQLException {
  Cache cache = ms.getCache();//二级缓存
  if (cache != null) {
    flushCacheIfRequired(ms);
    if (ms.isUseCache() && resultHandler == null) {
      ensureNoOutParams(ms, boundSql);
      @SuppressWarnings("unchecked")
      List<E> list = (List<E>) tcm.getObject(cache, key);//取出二级缓存的数据
      if (list == null) {
          //从一级缓存拿数据,拿不到从数据库查询
        list = delegate.query(ms, parameterObject, rowBounds, resultHandler, key, boundSql);
        tcm.putObject(cache, key, list); // 保存当前查询到的数据,待后续Session提交后做二级缓存
      }
      return list;
    }
  }
  return delegate.query(ms, parameterObject, rowBounds, resultHandler, key, boundSql);
}
```

#### 6.嵌套查询与嵌套结果有什么区别

基本回答:

嵌套结果 是一次查询 然后映射到对应属性
嵌套查询是多次查询 有N+1问题 解决方法就是使用懒加载和嵌套结果