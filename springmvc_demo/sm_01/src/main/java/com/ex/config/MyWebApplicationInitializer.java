package com.ex.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class MyWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
       super.onStartup(servletContext);
       FilterRegistration.Dynamic cFilter =
               servletContext.addFilter("characterEncodingFilter", CharacterEncodingFilter.class);
       cFilter.setInitParameter("encoding","UTF-8");
       cFilter.setInitParameter("forceEncoding", "true");
       cFilter.addMappingForUrlPatterns(null,false,"/*");


//        EnumSet<DispatcherType> dispatcherTypes = EnumSet.allOf(DispatcherType.class);
//        dispatcherTypes.add(DispatcherType.REQUEST);
//        dispatcherTypes.add(DispatcherType.FORWARD);
        //cFilter.addMappingForUrlPatterns(dispatcherTypes,true,"/*");

//        ServletRegistration aDefault = servletContext.getServletRegistration("default");
//        aDefault.addMapping("/page/*");
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

//    @Override
//    protected Filter[] getServletFilters() {
//        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//        characterEncodingFilter.setEncoding("UTF-8");
//        return new Filter[]{characterEncodingFilter};
//    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{ISpringConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{ISpringMvcConfig.class};
    }
}
