package com.mvc.mapper;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

class AccountMapperTest {
    SqlSession sqlSession;
    AccountMapper accountMapper;
    @BeforeEach
    void before(){
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("mybatis.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        sqlSession = sqlSessionFactory.openSession(true);
        accountMapper = sqlSession.getMapper(AccountMapper.class);
    }
    @AfterEach
    void after(){
        sqlSession.close();
    }

    @Test
    void findAccountByAid() {
        System.out.println(accountMapper.findAccountByAid(2));
    }
}