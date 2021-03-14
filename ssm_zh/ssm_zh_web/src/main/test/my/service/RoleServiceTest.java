package my.service;

import my.domain.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class RoleServiceTest {
    @Autowired
    RoleService roleService;
    @Test
    public void findAll() throws Exception {
        List<Role> all = roleService.findAll();
        for (Role role : all) {
            System.out.println(role);
        }
    }
}
