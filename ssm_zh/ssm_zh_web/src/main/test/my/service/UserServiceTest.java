package my.service;

import my.domain.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Test
    public void findAll() throws Exception {
        List<UserInfo> list = userService.findAll(1, 4);
        for (UserInfo userInfo : list) {
            System.out.println(userInfo);
        }
    }
    @Test
    public void findById() throws Exception {
        UserInfo userInfo = userService.findById("036E71474B011C3ACE6D859893B49168");
        System.out.println(userInfo);
    }
}
