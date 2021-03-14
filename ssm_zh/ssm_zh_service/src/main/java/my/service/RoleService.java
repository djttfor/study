package my.service;

import my.domain.Permission;
import my.domain.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll()throws Exception;

    Role findByRoleId(String id) throws Exception;

    String checkRoleNameExist(String roleName)throws Exception;

    int addRole(Role role);

    List<Permission> findPermissionByRole(String roleId);

    void addPermissionToRole(String roleId, String[] ids);
}
