package my.dao;

import my.domain.Permission;
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
public class PermissionDaoTest {
    @Autowired
    PermissionDao permissionDao;
    @Test
    public void findByRoleId() throws Exception {
        List<Permission> permissions = permissionDao.findByRoleId("1538AAC2DD996145FC4B7162CBF266EC");
        for (Permission permission : permissions) {
            System.out.println(permission);
        }
    }
    @Test
    public void findAll() throws Exception {
        List<Permission> all = permissionDao.findAll();
        for (Permission permission : all) {
            System.out.println(permission);
        }
    }
    @Test
    public void addPermission(){
        Permission p = new Permission();
        p.setPermissionName("hehe");
        p.setUrl("/aaa/bbb");
        int i = permissionDao.addPermission(p);
        System.out.println(i);
    }
    @Test
    public void checkUrlExist() throws Exception {
        String s = permissionDao.checkUrlExist("/product/show");
        System.out.println(s);
    }
    @Test
    public void showPermission(){
        Permission permission = permissionDao.findPermissionByPid("0F76B60D2BCC529E52C7F8823D3FFCEB");
        System.out.println(permission);
    }
}
