package my.service.impl;

import my.dao.AccountDao;
import my.domain.Account;
import my.service.AccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Resource(name = "accountDao")
    private AccountDao accountDao;

    @Override
    public List<Account> findAll() {
        return accountDao.findAll();
    }

    @Override
    public Account findAccountById(Integer id) {
        return null;
    }

    @Override
    public int saveAccount(Account account) {
        return accountDao.save(account);
    }

    @Override
    public int transfer(Integer out, Integer in, Double money) {
        //查询转出用户余额
        Double outMoney = accountDao.findMoneyById(out);
        if(outMoney<money||money<=0){
            return 3;//提示用户余额不足
        }
        //更新转出用户的余额
        int out1 = accountDao.transferOut(out, money);
        if (out1!=1){
            return -1;//转账出错
        }
        return accountDao.transferIn(in, money);
    }
}
