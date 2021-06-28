package com.mvc.mapper;

import com.mvc.pojo.Account;
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
    SqlSessionFactory sqlSessionFactory;
    @BeforeEach
    void before(){
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("mybatis.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        sqlSession = sqlSessionFactory.openSession(true);
        accountMapper = sqlSession.getMapper(AccountMapper.class);
    }
    @AfterEach
    void after(){
        sqlSession.close();
    }

    @Test
    void findAccountByAid() {
        Account accountByAid1 = accountMapper.findAccountByAid(3);
        System.out.println("第一次查询："+accountByAid1);
        sqlSession.commit();
//        SqlSession ss = sqlSessionFactory.openSession(true);
//        AccountMapper accountMapper2 = ss.getMapper(AccountMapper.class);
        Account accountByAid2 = accountMapper.findAccountByAid(3);
        System.out.println("第二次查询："+accountByAid2);
    }
}
