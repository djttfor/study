package com.demo.mybatis;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Demo3 {
    public static void main(String[] args) {
        // 保存生成的代理类的字节码文件
       // System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
//        AppMapper appMapper = (AppMapper) MyProxyFactory2.newInstance(new MyProxy(),AppMapper.class);
//        appMapper.findAll();
        AppMapper appMapper = (AppMapper) new MyProxy2(new AppMapperImpl()).getProxy();
        appMapper.findAll();
    }
}

interface AppMapper{
    void findAll();
}
class AppMapperImpl implements AppMapper{

    @Override
    public void findAll() {
        System.out.println("实现类执行了");
    }
}
class MyProxy implements InvocationHandler{
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method.getName()+"执行了");
        return null;
    }
}
class MyProxy2 implements InvocationHandler{
    Object target;

    public MyProxy2(Object target) {
        this.target = target;
    }

    public  Object getProxy(){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method.getName()+"执行前");
        method.invoke(target, args);
        return null;
    }
}
class MyProxyFactory{
    public static<T> T newInstance(MyProxy myProxy,Class<T> mapperInterface){
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(),new Class[]{mapperInterface},myProxy);
    }
}
class MyProxyFactory2{
    public static Object newInstance(MyProxy myProxy,Class<?> mapperInterface) {
        return Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, myProxy);
    }
}






