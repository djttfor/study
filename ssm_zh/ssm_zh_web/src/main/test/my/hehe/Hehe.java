package my.hehe;

import com.sun.xml.internal.xsom.impl.scd.Iterators;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
//import java.util.Map;

public class Hehe {
    public void a(){}
    public void a(int a){}
    public void a(Integer a){}
    public void a(Integer a, int b){}

    public <T> Class<T> hehe2(Class<T> clazz){
        if(clazz.getSimpleName() .equals("Integer")){
            clazz = (Class<T>) int.class;
        }
        System.out.println(clazz.getName());
        return clazz;
    }
    @Test
    public void hehe3(){
        hehe2(Integer.class);
    }

    @Test
    public void hehe() throws NoSuchMethodException {
        Class<Hehe> heheClass = Hehe.class;
        Class[] classes = new Class[2];
        classes[0] = Integer.class;
        classes[1] = int.class;
        Method method = heheClass.getMethod("a", classes);
        System.out.println(method.getName());

    }
    @Test
    public void demo1(){
        Map<String ,String> map = new HashMap<String ,String>();
        map.put("a","b");

        System.out.println(map.put("a","c"));
    }
}
