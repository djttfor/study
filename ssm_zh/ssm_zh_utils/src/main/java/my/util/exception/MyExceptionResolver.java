package my.util.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;


public class MyExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MyException myException = null;
        String message = null;
        ex.printStackTrace();
        if (ex instanceof MyException){
            myException = (MyException) ex;
        }else{
            //如果不是系统自定义异常,那就创建一个异常
            myException = new MyException("未知错误");
        }
        message = myException.getMessage();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("404_my");
        mv.addObject("message",message);
        return mv;
    }

    private String getExceptionAllInfo(Exception ex)  {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream pout = new PrintStream(out);
        ex.printStackTrace(pout);
        String ret = new String(out.toByteArray());
        pout.close();
        try {
            out.close();
        } catch (IOException e) {
        }
        return ret;
    }
}
