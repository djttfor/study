package my.service.impl;

import com.github.pagehelper.PageHelper;
import my.dao.UserDao;
import my.domain.Role;
import my.domain.UserInfo;
import my.service.UserService;
import my.util.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserInfo userInfo = null;
        User user = null;
        try {
            userInfo = userDao.findByUsername(username);
        } catch (Exception e) {
            try {
                throw new MyException("用户名没找到");
            } catch (MyException e1) {
                e1.printStackTrace();
            }
        }

        return new User(userInfo.getUsername(), userInfo.getPassword(), userInfo.getStatus() != 0,
                true, true, true, getAuthority(userInfo.getRoles()));
    }

    //作用就是返回一个List集合，集合中装入的是角色描述
    private List<SimpleGrantedAuthority> getAuthority(List<Role> roles) {

        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        }
        return list;
    }

    @Override
    public List<UserInfo>  findAll(int pageNum,int pageSize) throws Exception {
        PageHelper.startPage(pageNum, pageSize);
        return userDao.findAll();
    }

    @Override
    public UserInfo findById(String id) throws Exception {
        return userDao.findById(id);
    }

    @Override
    public int saveUser(UserInfo userInfo) throws Exception {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        return userDao.saveUser(userInfo);
    }

    @Override
    public String checkUsernameExist(String username) {
        return userDao.checkUsernameExist(username);
    }

    @Override
    public List<Role> findRolesByUser(String userId) {
        return userDao.findRolesByUser(userId);
    }

    @Override
    public int addRoleToUser(String userId, String[] ids) {
        int result = 0;
        for (String id : ids) {
            userDao.addRoleToUser(userId,id);
        }
        return result;
    }
}
