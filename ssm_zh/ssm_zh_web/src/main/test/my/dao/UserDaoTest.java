package my.dao;

import my.domain.Role;
import my.domain.UserInfo;
import my.util.PasswordEncoderUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class UserDaoTest {
    @Autowired
    UserDao userDao;

    @Test
    public void findByUsername() throws Exception {
        UserInfo userInfo = userDao.findByUsername("jack");
        System.out.println(userInfo);
    }
    @Test
    public void findAll() throws Exception {
        List<UserInfo> all = userDao.findAll();
        for (UserInfo userInfo : all) {
            System.out.println(userInfo);
        }
    }
    @Test
    public void saveUser(){
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail("12345@qq.com111");
        userInfo.setUsername("JkK");
        userInfo.setPassword(PasswordEncoderUtil.encodePassword("123"));
        userInfo.setPhoneNum("10087");
        userInfo.setStatus(1);
        int i = userDao.saveUser(userInfo);
        System.out.println(i);
    }

    @Test
    public void checkUsernameExist(){
        System.out.println(userDao.checkUsernameExist("jimmy"));
    }
    @Test
    public void findUserByRoleId() throws Exception {
        List<UserInfo> userInfos = userDao.findUserByRoleId("3D1296F6737866C82B716323E4698525");
        for (UserInfo userInfo : userInfos) {
            System.out.println(userInfo);
        }
    }
    @Test
    public void findRolesByUserId(){
        List<Role> roles = userDao.findRolesByUser("54AFDDDA17BCF820960D0622B6AD05EF");
        for (Role role : roles) {
            System.out.println(role);
        }
    }
    @Test
    public void addRoleToUser(){
        int i = userDao.addRoleToUser("CDBC17D45ED675E90B79570B8A6DA2E9", "D8B672FB511A37246B02EB739F4F4531");
        System.out.println(i);
    }
}
