package com.mvc.mapper;

import com.mvc.pojo.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import java.util.*;


class UserMapperTest {
    SqlSession sqlSession;
    UserMapper userMapper;
    SqlSessionFactory sqlSessionFactory;
    @BeforeEach
    void before(){
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("mybatis.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        sqlSession = sqlSessionFactory.openSession(true);
        userMapper = sqlSession.getMapper(UserMapper.class);
    }
    @AfterEach
    void after(){
        sqlSession.close();
    }

    @Test
    void findAll() {
        List<User> all = userMapper.findAll();
        for (User user : all){
            System.out.println(user);
        }
    }
    @Test
    void insert(){
        User user = new User();
        user.setUsername("张飞");
        user.setPassword("123");
        user.setAddress("荆州");
        user.setPhone("100987");
        System.out.println(userMapper.insertUser(user));
    }

    @Test
    void findUserById(){
        User user = userMapper.findUserById(3);
        System.out.println(user);
    }
    @Test
    void findUser(){
        User u = new User();
//        u.setPassword("123");
//        u.setUsername("jimmy");
        List<User> user = userMapper.findUser(u);
        for (User user1 : user) {
            System.out.println(user1);
        }
    }
    @Test
    void findUser2(){
        User u = new User();
        u.setPassword("123");
      //  u.setUsername("jimmy");
        List<User> user = userMapper.findUser2(u);
        for (User user1 : user) {
            System.out.println(user1);
        }
    }
    @Test
    void findByRange(){
        List<Integer> list = new ArrayList<>();
        List<User> user = userMapper.findByRange(list);
        for (User user1 : user) {
            System.out.println(user1);
        }
    }
    @Test
    void insertBatch(){
        List<User> list = new ArrayList<>();
        for(int i =40;i<100;i++){
            User u = new User();
            u.setUsername("lisi"+i);
            u.setPassword("00"+i);
            u.setAddress("东京"+i);
            u.setPhone("11"+i);
            list.add(u);
        }
        System.out.println(userMapper.batchInsert(list));
    }
    @Test
    void findUserByCondition(){
        User jimmy = userMapper.findUserByCondition(3, "jimmy");
        System.out.println("第一次查询:"+jimmy);
        User jimmy2 = userMapper.findUserByCondition(3, "jimmy");
        System.out.println("第二次查询:"+jimmy2);
        sqlSession.commit();
        User jimmy3 = userMapper.findUserByCondition(3, "jimmy");
        System.out.println("第三次查询:"+jimmy3);
//        SqlSession ss = sqlSessionFactory.openSession(true);
//        UserMapper userMapper1 = ss.getMapper(UserMapper.class);
//        User jimmy3 = userMapper1.findUserByCondition(3, "jimmy");
//        System.out.println("第三次查询:"+jimmy3);
//        User jimmy4 = userMapper1.findUserByCondition(3, "jimmy");
//        System.out.println("第四次查询:"+jimmy4);
    }
    @Test
    void updateUser(){
        User u = new User();
        u.setPhone("10087");
        u.setUsername("AAA");
        u.setPassword("123");
        u.setAddress("");
        u.setId(6);
        System.out.println(userMapper.updateUser(u));
    }
    @Test
    void insertUser(){
        User u = new User();
        u.setPhone("10087");
        u.setUsername("AAA");
        u.setPassword("123");
        u.setAddress("");
        //u.setId(6);
        System.out.println(userMapper.saveUser(u));
        System.out.println(u);
    }
}
