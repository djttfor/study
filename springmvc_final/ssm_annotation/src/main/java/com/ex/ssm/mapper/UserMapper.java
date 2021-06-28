package com.ex.ssm.mapper;

import com.ex.ssm.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ttfor
 * @since 2021-02-15
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<User> queryAllByNestedResult();
    List<User> queryAllByNestedQuery();
    @Select("select\n" +
            "            u.id uid ,username ,u.password userPassword ,u.phone,u.address,\n" +
            "            a.account_name accountName,a.password accountPassword,a.balance,\n" +
            "            m.member_name memberName,m.desc memberDesc\n" +
            "        from\n" +
            "            user u left JOIN account a on u.id = a.uid\n" +
            "                   left join member m on u.id = m.uid;")
    @ResultMap("allByNestedResult")
    List<User> aQueryAllByNestedResult();
    @Select("  select\n" +
            "               u.id uid ,username ,u.password userPassword ,u.phone,u.address\n" +
            "        from\n" +
            "               user u")
    @ResultMap("allByNestedQuery")
    List<User> aQueryAllByNestedQuery();

    int insertBatch(List<User> users);

    @Select("select count(*) from user")
    int findCount();

}