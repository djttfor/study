package com.ex.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ex.entity.User;
import com.ex.mapper.UserMapper;
import com.ex.service.ProductService;
import com.ex.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    ProductService productService;

    @Override
    //@Transactional
    public Boolean updatePassword(Long uid, String password) {
        int update = userMapper.updateById(new User().setId(uid).setPassword(password));
        productService.decrementCount(1,200);
        int a = 1/0;
        return update>0;
    }

    @Override
    public Boolean doDecrementCount(int pid, int count)
    {
        ProductServiceImpl p = (ProductServiceImpl) this.productService;
        return p.doDecrementCount(1,200);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean updateCredit(Long pid, int credit) {
        int update = userMapper.update(new User(), Wrappers.lambdaUpdate(User.class)
                .setSql("credit = credit - " + credit).eq(User::getId, pid));
        productService.decrementCount(1,100);
        int a = 1/0;
        return update>0;
    }
}

@Slf4j
class A{
    public static void main(String[] args) throws Exception {
        HttpURLConnection connection =
                (HttpURLConnection) new URL("http://localhost:8086/api/plumemo-service/archive/archive/v1/list")
                        .openConnection();
        //write header
        connection.setRequestProperty("Content-Type", "application/json");

        //set timeout
        connection.setConnectTimeout(1000 * 5);
        connection.setReadTimeout(1000 * 10);

        //connect
        connection.connect();

        int responseCode = connection.getResponseCode();

        log.info("response code : {}", responseCode);

        // read response
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } finally {
            connection.disconnect();
        }
    }
}
