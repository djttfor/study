package my.dao;

import my.domain.Account;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountDao {
    List<Account> findAll();
    Account findAccountById(Integer id);
    int save(Account account);
    int transferIn(@Param("aid") Integer id, @Param("money") Double money);
    int transferOut(@Param("aid") Integer id, @Param("money") Double money);
    Double findMoneyById(Integer id);
}
