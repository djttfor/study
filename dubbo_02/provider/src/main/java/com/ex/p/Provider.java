package com.ex.p;

import com.ex.pojo.Brand;
import com.ex.service.ProductService;
import javafx.application.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class Provider {
    public static void main(String[] args) throws IOException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-provider.xml");
        System.out.println("Provider 服务启动了-----------------");
        System.in.read();
    }
}
