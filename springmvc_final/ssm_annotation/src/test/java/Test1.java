import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ex.ssm.config.ISpringConfig;
import com.ex.ssm.entity.Member;
import com.ex.ssm.entity.User;
import com.ex.ssm.mapper.AccountMapper;
import com.ex.ssm.mapper.MemberMapper;
import com.ex.ssm.mapper.UserMapper;
import com.ex.ssm.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ISpringConfig.class})
public class Test1 {
    private static final Logger log = LoggerFactory.getLogger(Test1.class);
    @Autowired
    UserService userService;

    @Autowired(required = false)
    UserMapper userMapper;

    @Autowired(required = false)
    AccountMapper accountMapper;

    @Resource
    MemberMapper memberMapper;

    @Test
    public void query(){
        Page<User> page = new Page<>(1,5);
        Page<User> userPage = userMapper.selectPage(page, Wrappers.lambdaQuery());
        log.info("查询到的记录:{}",userPage.getRecords());
        log.info("当前页:{}",userPage.getCurrent());
        log.info("每页显示条数:{}",userPage.getSize());
        log.info("总条数:{}",userPage.getTotal());
    }
    @Test
    public void query2(){
        User u = new User();
        u.setUsername("张");
        Page<User> page = userService.selectLikePage(u, 1, 10);

        System.out.println(page.getRecords());
    }

    @Test
    public void query3(){
        List<User> users = userMapper.aQueryAllByNestedResult();
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void query4(){
        List<Member> members = null;
        try {
            members = memberMapper.queryMemberByUid(1);
        } catch (Throwable e) {
            StringWriter sw = new StringWriter();
            PrintWriter p = new PrintWriter(sw);

        }

    }

    @Test
    public void query5(){
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            User user = new User();
            user.setUsername("张三"+(i+1));
            user.setPassword("123"+i);
            user.setAddress("北京天安门城南"+(i+1)+"号");
            user.setPhone("1008"+i);
            users.add(user);
        }
        System.out.println(userMapper.insertBatch(users));
    }

    public static void main(String[] args) throws InterruptedException ,  IOException {

    }

}



