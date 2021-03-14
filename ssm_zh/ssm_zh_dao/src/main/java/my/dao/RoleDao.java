package my.dao;

import my.domain.Permission;
import my.domain.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RoleDao {

    //根据用户id查询出所有对应的角色
    @Select("select * from role where id in (select role_id from role_users where users_id=#{id})")
    @Results(id = "roleMap",value = {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "roleName",column = "role_name"),
            @Result(property = "roleDesc",column = "role_desc"),
            @Result(property = "permissions",column = "id",javaType = java.util.List.class,
                    many = @Many(select = "my.dao.PermissionDao.findByRoleId"))
    })
    List<Role> findRoleByUserId(String userId) throws Exception;

    @Select("select * from role")
    @ResultMap("roleMap")
    List<Role> findAll() throws Exception;

    /**
    * Description:
    * <根据roleId查询role详情>
    * @param id roleId
    * @return:  Role
    * @Author: DJ
    * @Date: 2020-04-06 16:14
    */
    @Select("select * from role where id = #{id}")
    @Results(id = "roleMapByRid" ,value = {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "roleName",column = "role_name"),
            @Result(property = "roleDesc",column = "role_desc"),
            @Result(property = "permissions",column = "id",javaType = java.util.List.class,
                    many = @Many(select = "my.dao.PermissionDao.findByRoleId")),
            @Result(property = "users" ,column = "id" ,javaType = java.util.List.class,
                    many = @Many(select = "my.dao.UserDao.findUserByRoleId")
            )
    })
    Role findByRoleId(String id);

    @Select("select role_name from role where role_name = #{roleName}")
    String checkRoleNameExist(String roleName);

    @Insert("insert into role(role_name,role_desc) values(#{roleName},#{roleDesc})")
    int addRole(Role role);

    @Select("select * from role where id in (select role_id from role_permission where permission_id = #{id})")
    @Results(id = "hehe1" ,value = {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "roleName",column = "role_name"),
            @Result(property = "roleDesc",column = "role_desc")
    })
    List<Role> findRoleByPermissionId(String PermissionId);

    @Select("select * from permission where id not in (select permission_id from role_permission where role_id = #{roleId})")
    @Results(
            id = "fpbr",value = {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "permissionName",column = "permission_name"),
            @Result(property = "url",column = "url")
    }
    )
    List<Permission> findPermissionByRole(String roleId);
    @Insert("insert into role_permission(role_id,permission_id) values(#{roleId},#{permissionId})")
    void addPermissionToRole(@Param("roleId")String roleId,@Param("permissionId") String permissionId);
}
