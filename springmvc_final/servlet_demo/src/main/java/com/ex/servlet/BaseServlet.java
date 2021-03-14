package com.ex.servlet;

import com.ex.custom.MyException;
import com.ex.util.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class BaseServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(BaseServlet.class);
    @Override
    public final void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        try {
            int i = uri.lastIndexOf('/');
            if(i==-1){
                throw new MyException("请求的路径有误:"+uri);
            }
            //拿到请求的方法
            String methodName = uri.substring(i + 1);
            if(req.getSession().getServletContext().getContextPath().equals(methodName)){
                throw new MyException("不能请求根路径");
            }
            //通过子类获取方法
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            if(Modifier.isPrivate(method.getModifiers())){
                throw new MyException("执行的方法不能是私有的!");
            }
            method.invoke(this,req,resp);

        } catch (NoSuchMethodException e) {
            log.error("没有找到这个方法,检查你的URI:{},请检查:{}",uri, ServletUtil.getExceptionMessage(e));
            req.getRequestDispatcher("/page/404.html").forward(req,resp);
            //throw new MyException("没有找到这个方法,请检查:",e);
        } catch (Throwable e){
            log.error("执行你这个方法出错了{},请检查:{}",uri,ServletUtil.getExceptionMessage(e));
            req.getRequestDispatcher("/page/404.html").forward(req,resp);
            //throw new MyException("出错了,请自己看看:",e);
        }

    }
}
