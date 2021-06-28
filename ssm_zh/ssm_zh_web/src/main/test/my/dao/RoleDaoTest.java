package my.dao;

import my.domain.Permission;
import my.domain.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class RoleDaoTest {
    @Autowired
    RoleDao roleDao;
    @Test
    public void findRoleByUserId() throws Exception {
        List<Role> role = roleDao.findRoleByUserId("036E71474B011C3ACE6D859893B49168");
        for (Role role1 : role) {
            System.out.println(role1);
        }
    }
    @Test
    public void findAll() throws Exception {
        List<Role> roleList = roleDao.findAll();
        for (Role role : roleList) {
            System.out.println(role);
        }
    }
    @Test
    public void findRoleByRoleId(){
        Role byRoleId = roleDao.findByRoleId("3D1296F6737866C82B716323E4698525");
        System.out.println(byRoleId);
    }
    @Test
    public void checkRoleNameExist(){
        roleDao.checkRoleNameExist("ADMIN");
    }
    @Test
    public void addRole(){
        Role r = new Role();
        r.setRoleName("一级用户");
        r.setRoleDesc("最基础的用户");
        int result = roleDao.addRole(r);
        System.out.println(result);
    }
    @Test
    public void findPermissionByRole(){
        List<Permission> plist = roleDao.findPermissionByRole("1538AAC2DD996145FC4B7162CBF266EC");
        for (Permission permission : plist) {
            System.out.println(permission);
        }
    }
    @Test
    public void addPermissionToRole(){
        roleDao.addPermissionToRole("D8B672FB511A37246B02EB739F4F4531", "C875174B0B7758BBCDB53AE7C7515280");
    }
}
