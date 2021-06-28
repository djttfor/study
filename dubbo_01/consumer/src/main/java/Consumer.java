
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;

public class Consumer {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        System.out.println("dubbo服务消费端已启动...");
//        BrandService brandService = (BrandService) context.getBean("brandService");// 获取远程服务代理
//        List<Brand> all = brandService.findAll();//执行远程方法
//        for (Brand brand : all) {
//            System.out.println(brand);
//        }//显示调用结果
        System.in.read(); // 按任意键退出
    }
}
