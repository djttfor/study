package my.service.impl;

import my.domain.Account;
import my.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.annotation.Resource;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class AccountServiceImplTest {

    @Resource(name = "accountService")
    AccountService accountService;
    @Test
    public void findAll() {
        List<Account> accounts = accountService.findAll();
        for (Account account : accounts) {
            System.out.println(account);
        }
    }
    @Test
    public void findAccountById() {
        Account account = accountService.findAccountById(2);
        System.out.println(account);
    }
    @Test
    public void saveAccount() {
        Account account = new Account();
        account.setUid(69);
        account.setAcname("呵呵");
        account.setMoney(1223.0);
        account.setPassword("123456");
        accountService.saveAccount(account);
    }
    @Test
    public void transfer() {
        int transfer = accountService.transfer(1, 2, 105.0);
        System.out.println(transfer);
    }
    @Test
    public void demo1(){
        Integer a = 1;
        Integer b = 2;
        int f = 3;
        Integer c = new Integer(4);
        Integer d = new Integer(6);
       // System.out.println(b==f);
        System.out.println(d==c+b);
       // System.out.println(b+c==a+d);
    }
}