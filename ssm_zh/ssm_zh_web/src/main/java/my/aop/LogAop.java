package my.aop;

import my.controller.SyslogController;
import my.domain.Syslog;
import my.service.SysService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component("logAop")
@Aspect
public class LogAop {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    SysService sysService;

    private Date startDate;//访问时间
    private Class executionClass;//访问的类
    private Method executionMethod;//访问的方法

    /**
     * 获取开始访问时间,访问的类及方法
     * @param jp
     * @throws NoSuchMethodException
     */
    @Before("execution(* my.controller.*.*(..))")
    public void doBefore(JoinPoint jp) throws NoSuchMethodException {
        //获取访问时间
        startDate = new Date();
        //获取访问的类
        executionClass = jp.getTarget().getClass();
        //获取访问的方法的名称
        String methodName = jp.getSignature().getName();
        //获取访问的方法的参数
        Object[] objects = jp.getArgs();
        if (objects==null || objects.length==0){//无参数
            executionMethod = executionClass.getMethod(methodName);
        }else{
            //如果有参数就创建一个字节码数组,通过参数字节码数组来获取该方法
            Class[] classArgs = new Class[objects.length];
            for(int i= 0;i<classArgs.length;i++){
                //将Integer.class转为int.class
                classArgs[i] = isIntegerClass(objects[i].getClass());
            }
            executionMethod = executionClass.getMethod(methodName, classArgs);

        }
    }

    /**
     * 以后转换都用包装做参数,避免int.class与Integer.class的问题
     * @param clazz
     * @return Class<T>
     */
    public <T> Class<T> isIntegerClass(Class<T> clazz){
        if("Integer".equals(clazz.getSimpleName())){
            clazz = (Class<T>) int.class;
        }
        return clazz;
    }



    /**
     * 获取访问url,时长及IP
     * @param jp
     */
    @After("execution(* my.controller.*.*(..))")
    public void doAfter(JoinPoint jp){
        if(executionClass != SyslogController.class){
            //获取类上面的RequestMapping对象
            RequestMapping classAnnotation = (RequestMapping) executionClass.getAnnotation(RequestMapping.class);
            if (classAnnotation!=null){
                //获取方法上的RequestMapping对象
                RequestMapping methodAnnotation = executionMethod.getAnnotation(RequestMapping.class);
                if(methodAnnotation!=null){
                    String url = "";
                    url = classAnnotation.value()[0] + methodAnnotation.value()[0];
                    //封装日志对象
                    Syslog syslog = new Syslog();
                    //获取访问时长
                    long executionTime = new Date().getTime() - startDate.getTime();
                    syslog.setExecutionTime(executionTime);
                    syslog.setUrl(url);
                    syslog.setMethod(executionClass.getName()+"."+executionMethod.getName());
                    syslog.setVisitTime(startDate);
                    syslog.setIp(request.getRemoteAddr());
                    // 可以通过securityContext获取用户名，也可以从request.getSession中获取
                    SecurityContext context = SecurityContextHolder.getContext();
                    String username = ((User) (context.getAuthentication().getPrincipal())).getUsername();
                    syslog.setVisitor(username);
                    //调用service将日志保存到数据库
                    sysService.saveLog(syslog);
                }
            }
        }
    }
}
