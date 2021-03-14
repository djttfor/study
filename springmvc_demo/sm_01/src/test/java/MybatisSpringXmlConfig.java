import com.ex.mapper.UserMapper;
import com.ex.pojo.Bird;
import com.ex.pojo.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;


@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:spring-context.xml")
public class MybatisSpringXmlConfig {
    @Autowired
    UserMapper userMapper;
    @Autowired @Qualifier("sqlSessionFactoryBean")
    SqlSessionFactoryBean sqlSessionFactoryBean;
    @Autowired
    Bird bird ;
    @Test
    public void demo1(){
        System.out.println(bird);
    }
    @Test
    public void findAll() throws Exception {

        User jimmy = userMapper.findUserByCondition(3, "jimmy");
        System.out.println("第一次查询:"+jimmy);
        User jimmy2 = userMapper.findUserByCondition(3, "jimmy");
        System.out.println("第二次查询:"+jimmy2);
        //sqlSession.commit();
        User jimmy3 = userMapper.findUserByCondition(3, "jimmy");
        System.out.println("第三次查询:"+jimmy3);
    }
    @Test
    public void nestedQuery(){
        List<User> users = userMapper.findAll2();
        for (User user : users) {
            System.out.println(user);
        }
    }
}
