package com.mvc.mapper;

import com.mvc.pojo.Account;
import com.mvc.pojo.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface UserMapper {
//    @Select("select * from user")
//    @Results (id = "userMap" ,value = {
//            @Result(property = "id" ,column = "id",id = true),
//            @Result(property = "username",column = "username"),
//            @Result(property = "password",column = "password"),
//            @Result(property = "account",column = "id",javaType = com.mvc.pojo.Account.class,
//            one = @One(select = "com.mvc.mapper.AccountMapper.findAccountByAid")),
//            @Result(property = "members", column = "id",javaType = java.util.List.class,
//            many = @Many(select = "com.mvc.mapper.MemberMapper.findMemberByUid"))
//    })
   List<User> findAll();
   User findUserById(Integer uid);
   List<User> findUser(User user);
   List<User> findUser2(User user);
   List<User> findByRange(List<Integer> list);
    @Insert("insert into user(username,password,address,phone) values(#{username},#{password},#{address},#{phone})")
    Integer insertUser(User user);
    Integer batchInsert(List<User> users);
    User findUserByCondition(Integer uid,String name);
    Integer updateUser(User user);
    Integer saveUser(User user);
}
