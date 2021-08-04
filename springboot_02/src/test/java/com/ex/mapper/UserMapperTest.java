package com.ex.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ex.entity.User;
import com.ex.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class UserMapperTest {
    @Autowired(required = false)
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Test
    public void findAll(){
        List<User> users = userMapper.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void list(){
        List<User> users = userMapper.selectList(null);
        for (User user : users) {
            System.out.println(user);
        }
    }
    @Test
    public void insert(){
        User user = new User();
        user.setUsername("aaa");
        user.setPassword("aaa");
        System.out.println(userMapper.insert(user));
    }

    @Test
    public void update(){
        User user = new User();
        user.setUsername("aaa");
        user.setPassword("222");
        user.setId(6L);
        System.out.println(userMapper.updateById(user));
    }

    @Test
    public void delete(){
        System.out.println(userService.update(Wrappers.lambdaUpdate(User.class)
                .set(User::getPassword,"12318").eq(User::getId, 2)));
    }

    @Test
    public void test1(){
        System.out.println(userService.updatePassword(1L, "1234"));
    }

    @Test
    public void test2(){
        System.out.println(userService.getOne(Wrappers.lambdaQuery(User.class).eq(User::getId, 1)));
    }
}

class A{
    private String name;

    public A(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "A{" +
                "name='" + name + '\'' +
                '}';
    }

    public static void main(String[] args) {
        A a = new A("aaa");
        System.out.println(a);
        a.change(a);
        System.out.println(a);
        a.name = null;
        System.out.println(a);
        int[] arr = new int[5];
        System.out.println(Arrays.toString(a.changeArr(arr)));
    }
    public void change(A a){
        a = null;
    }
    public int[] changeArr(int[] arr){
        int index = 0;
        for (int i : arr) {
            arr[index] = index++;
        }
        return arr;
    }
}

