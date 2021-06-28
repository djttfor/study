import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ex.ssm.entity.Product;
import com.ex.ssm.entity.User;
import com.ex.ssm.mapper.UserMapper;
import com.ex.ssm.service.AccountService;
import com.ex.ssm.service.ProductService;
import com.ex.ssm.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:spring.xml")
public class test1 {
    private static final Logger log = LoggerFactory.getLogger(test1.class);
    @Autowired
    UserMapper userMapper;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    AccountService accountService;
    @Test
    public void test1(){
        Page<Product> page = new Page<>(1,10);
        Page<Product> productPage = productService.page(page);
        List<Product> records = productPage.getRecords();
        for (Product record : records) {
            System.out.println(record);
        }

    }
    @Test
    public void test2(){
        Page<User> page = userService.pageQuery(1,5);
        List<User> records = page.getRecords();
        long total = page.getTotal();
        long current = page.getCurrent();
        long size = page.getSize();
        log.info("总页数：{}，当前页：{}，当前记录数：{}",total,current,size);
        for (User record : records) {
            System.out.println(record);
        }
    }
}
