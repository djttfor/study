import com.ex.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:spring-context.xml")
public class AopTransactionXmlConfigTest {
    @Autowired
    AccountService accountService;
    @Test
    public void transfer(){
        Boolean transfer = accountService.transfer(1, 3, 100L);
        System.out.println(transfer);
    }
}
