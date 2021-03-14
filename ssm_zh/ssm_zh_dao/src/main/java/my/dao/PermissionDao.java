package my.dao;

import my.domain.Permission;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PermissionDao {
    @Select("select * from permission where id in (select permission_id from role_permission where role_id = #{roleId})")
    @Results(id = "permissionMap" ,value = {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "permissionName",column = "permission_name"),
            @Result(property = "url",column = "url")
    })
    List<Permission> findByRoleId(String roleId)throws Exception;

    @Select("select * from permission")
    @Results(id = "permissionAllMap" ,value = {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "permissionName",column = "permission_name"),
            @Result(property = "url",column = "url")
    })
    List<Permission> findAll()throws Exception;

    @Select("select url from permission where url = #{url}")
    String checkUrlExist(String url)throws Exception;

    @Insert("insert into permission(permission_name,url) values(#{permissionName},#{url})")
    int addPermission(Permission permission);

    @Select("select * from permission where id = #{id}")
    @Results(id = "hehe" ,value = {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "permissionName",column = "permission_name"),
            @Result(property = "url",column = "url"),
            @Result(property = "roles",column = "id",javaType = java.util.List.class,
            many = @Many(select = "my.dao.RoleDao.findRoleByPermissionId"))
    })
    Permission findPermissionByPid(String id);
}
