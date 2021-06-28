package my.service;

import my.domain.Permission;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class PermissionServiceTest {
    @Autowired
    private PermissionService permissionService;
    @Test
    public void findAll() throws Exception {
        List<Permission> all = permissionService.findAll(1, 3);
        for (Permission permission : all) {
            System.out.println(permission);
        }
    }
}
