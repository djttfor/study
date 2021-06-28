package com.mvc.mapper;

import com.mvc.pojo.Role;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper {
    @Select("select * from role where uid = #{uid}")
    @Results(id = "roleMap" ,value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "role_name",property = "roleName"),
            @Result(column = "uid",property = "uid")
    })
    List<Role> findRoleByUid(Integer uid);
}
