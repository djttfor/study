import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ex.smp.config.ISpringConfig;
import com.ex.smp.entity.Account;
import com.ex.smp.mapper.AccountMapper;
import com.ex.smp.service.IAccountService;
import com.ex.smp.service.IUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ISpringConfig.class})
//@ContextConfiguration("classpath:spring-context.xml")
public class MybatisPlusSpringJavaConfigTest {

    @Autowired
    IAccountService accountService;

    @Autowired
    IUserService userService;

    @Autowired
    AccountMapper accountMapper;
    @Test
    public void list() throws SQLException {
        LambdaUpdateWrapper<Account> l = Wrappers.lambdaUpdate(Account.class);
        l.setSql("balance = 10000");
        accountService.update(l);
    }
    @Test
    public void transfer(){
        System.out.println(accountService.transfer(3, 1, 100L));
    }

    @Test
    public void transfers() throws InterruptedException {
        long start = System.currentTimeMillis();
        CountDownLatch child = new CountDownLatch(2);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for(int i=0;i<2;i++){
            executorService.execute(()->{
                accountService.transfer2(3, 1, 100L);
                child.countDown();
            });
        }
        child.await();
        System.out.println(System.currentTimeMillis()-start+"ms");
    }

    public static void main(String[] args) {
//        AnnotationConfigApplicationContext an =
//                new AnnotationConfigApplicationContext(ISpringConfig.class);
//        DataSourceTransactionManager dataSourceTransactionManager =
//                (DataSourceTransactionManager) an.getBean("transactionManager");
//        System.out.println(an.getBean(DataSource.class));
//        System.out.println(dataSourceTransactionManager);
//        System.out.println(an.getBean("selfTransactionManager"));
    }

}
