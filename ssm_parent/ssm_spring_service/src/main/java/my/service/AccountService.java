package my.service;

import my.domain.Account;

import java.util.List;

public interface AccountService {
    List<Account> findAll();
    Account findAccountById(Integer id);
    int saveAccount(Account account);
    int transfer(Integer out, Integer in, Double money);
}
