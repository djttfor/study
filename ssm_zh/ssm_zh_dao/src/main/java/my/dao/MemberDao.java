package my.dao;

import my.domain.Member;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;

public interface MemberDao {
    @Select("select * from member where id = #{id}")
    @Results(id = "memberByIdMap" ,value = {
            @Result(property = "id",column = "id" ,id = true),
            @Result(property = "name",column = "name"),
            @Result(property = "nickName",column = "nick_name"),
            @Result(property = "phoneNumber",column = "phone_number"),
            @Result(property = "email",column = "email")
    })
    Member findMemberById(String id) throws Exception;
}
