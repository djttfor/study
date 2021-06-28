package com.mvc.mapper;

import com.mvc.pojo.Member;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MemberMapper {
    @Select("select * from member where uid=#{uid}")
    @Results(id = "memberMap",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "member_name",property = "memberName"),
            @Result(column = "desc",property = "desc")
    })
    List<Member> findMemberByUid(Integer uid);
}
