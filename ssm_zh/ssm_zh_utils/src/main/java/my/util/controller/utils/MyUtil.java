package my.util.controller.utils;

import com.github.pagehelper.PageInfo;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MyUtil<T> {
    /**
     *  分页查询所有的代码抽取,传递到页面的集合叫pageInfo
     * @param list 封装到页面的数据
     * @param pageName 要跳转的页面
     * @return ModelAndView
     */
    public ModelAndView findAllPaging(List<T> list,String pageName){
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName(pageName);
        return mv;
    }
    /**
     * Description:
     * <查看xxx详情>
     * @param t 封装到页面的数据
     * @param attributeName 封装到页面的bean的名称
     * @param viewName 要跳转的页面
     * @return: org.springframework.web.servlet.ModelAndView
     * @Author: DJ
     * @Date: 2020-04-07 19:53
     */
    public ModelAndView show(T t,String attributeName,String viewName){
        ModelAndView mv = new ModelAndView();
        mv.addObject(attributeName, t);
        mv.setViewName(viewName);
        return mv;
    }

}
