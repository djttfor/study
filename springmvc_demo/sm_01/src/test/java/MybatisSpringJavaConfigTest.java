import com.ex.config.ISpringConfig;
import com.ex.mapper.UserMapper;
import com.ex.pojo.Bird;
import com.ex.pojo.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigInteger;
import java.util.List;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ISpringConfig.class})
public class MybatisSpringJavaConfigTest {
   @Autowired
    UserMapper userMapper;
   @Autowired
   Bird bird ;
    @Test
    public void demo1(){
        System.out.println(bird);
    }
    @Test
    public void findAll(){
        User jimmy = userMapper.findUserByCondition(3, "jimmy");
        System.out.println("第一次查询:"+jimmy);
        User jimmy2 = userMapper.findUserByCondition(3, "jimmy");
        System.out.println("第二次查询:"+jimmy2);
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
    @Test
    public void saveUser2(){
        User user =new User();
      //  user.setId(2147483646);
        BigInteger bigInteger = new BigInteger("1222");
        System.out.println(userMapper.saveUser2(user));
    }


}
