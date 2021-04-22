import com.ex.config.ISpringConfig;
import com.ex.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ISpringConfig.class})
public class ITest {


    @Autowired
    Account account;

    @Test
    public void test2(){
        System.out.println(account);
    }
}
