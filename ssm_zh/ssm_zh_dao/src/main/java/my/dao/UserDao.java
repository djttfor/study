package my.dao;

import my.domain.Role;
import my.domain.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserDao {

    @Select("select * from users where username=#{username}")
    @Results(id = "userInfoMap" ,value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "phoneNum", column = "phone_num"),
            @Result(property = "status", column = "status"),
            @Result(property = "roles",column = "id",javaType = java.util.List.class,many = @Many(select = "my.dao.RoleDao.findRoleByUserId"))
    })
    public UserInfo findByUsername(String username) throws Exception;

    @Select("select * from users")
    @ResultMap("userInfoMap")
    List<UserInfo> findAll()throws Exception;

    @Select("select * from users where id = #{id}")
    @ResultMap("userInfoMap")
    UserInfo findById(String id)throws Exception;

    @Insert("insert into users(email,username,password,phone_num,status) values(" +
            "#{email},#{username},#{password},#{phoneNum},#{status})")
    int saveUser(UserInfo userInfo);

    @Select ("select username from users where username = #{username}")
    String checkUsernameExist(String username);

    @Select("select * from users where id in (select users_id from role_users where role_id = #{roleId})")
    List<UserInfo> findUserByRoleId(String roleId)throws Exception;

    @Select("select * from role where id not in (select role_id from role_users where users_id = #{userId})")
    @Results(
            id = "frsbu",value = {
                    @Result(id = true,property = "id",column = "id"),
            @Result(property = "roleName",column = "role_name"),
            @Result(property = "roleDesc",column = "role_desc")
    }
    )
    List<Role> findRolesByUser(String userId);

    @Insert("insert into role_users(role_id,users_id) values(#{roleId},#{usersId})")
    int addRoleToUser(@Param("usersId") String userId, @Param("roleId") String roleId);
}
