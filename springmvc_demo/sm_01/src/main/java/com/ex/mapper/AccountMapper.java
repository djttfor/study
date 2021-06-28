package com.ex.mapper;


import com.ex.pojo.Account;
import org.apache.ibatis.annotations.*;

public interface AccountMapper {
    @Select("select * from account where id = #{aid}")
    @Results(id = "accountMap" ,value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "account_name",property = "accountName"),
            @Result(column = "password",property = "password"),
            @Result(column = "balance" ,property = "balance")
    })
    Account findAccountByAid(Integer aid);

    @Update("update account set balance = #{money} where id= #{id}")
    Integer updateAccountBalance(@Param("id")Integer aid, @Param("money") Long money);
}
