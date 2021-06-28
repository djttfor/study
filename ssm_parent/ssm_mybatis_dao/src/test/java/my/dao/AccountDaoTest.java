package my.dao;

import my.domain.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class AccountDaoTest {
    @Autowired
    AccountDao accountDao;
    @Test
    public void findAll(){
        List<Account> accountDaoAll = accountDao.findAll();
        for (Account account : accountDaoAll) {
            System.out.println(account);
        }
    }

    //测试git修改提交
    @Test
    public void save(){
       //测试修改后拉取
    }
    
}
