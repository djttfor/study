package com.mvc.mapper;

import com.mvc.pojo.Account;

public interface AccountMapper {
//    @Select("select * from account where id = #{aid}")
//    @Results(id = "accountMap" ,value = {
//            @Result(id = true,column = "id",property = "id"),
//            @Result(column = "account_name",property = "accountName"),
//            @Result(column = "password",property = "password")
//    })
    Account findAccountByAid( Integer aid);

}
