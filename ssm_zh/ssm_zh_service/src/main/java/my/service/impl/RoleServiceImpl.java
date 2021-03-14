package my.service.impl;

import my.dao.RoleDao;
import my.domain.Permission;
import my.domain.Role;
import my.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleDao roleDao;
    @Override
    public List<Role> findAll() throws Exception {
        return roleDao.findAll();
    }

    @Override
    public Role findByRoleId(String id) throws Exception {
        return roleDao.findByRoleId(id);
    }

    @Override
    public String checkRoleNameExist(String roleName) throws Exception {
        return roleDao.checkRoleNameExist(roleName);
    }

    @Override
    public int addRole(Role role) {
        return roleDao.addRole(role);
    }

    @Override
    public List<Permission> findPermissionByRole(String roleId) {
        return roleDao.findPermissionByRole(roleId);
    }

    @Override
    public void addPermissionToRole(String roleId, String[] ids) {
            for (String id : ids) {
                roleDao.addPermissionToRole(roleId,id);
            }
    }


}
