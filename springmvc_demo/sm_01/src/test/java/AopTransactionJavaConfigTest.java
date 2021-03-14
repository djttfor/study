import com.ex.config.ISpringConfig;
import com.ex.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ISpringConfig.class})
public class AopTransactionJavaConfigTest {
    @Autowired
    AccountService accountService;

    @Test
    public void demo(){
        System.out.println(accountService.transfer(1, 3, 1000L));
    }
}
