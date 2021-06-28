package com.demo.mybatis;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.mvc.pojo.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:springcontext.xml")
public class Demo2 {
    @Autowired
    DruidDataSource dataSource;
    @Autowired
     User user;
    @Test
    public void demo1() throws SQLException {
        DruidPooledConnection connection = dataSource.getConnection();
        String sql = "select * from user where id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1,10);
        ps.execute();
        ResultSet rs = ps.getResultSet();
        while(rs.next()){
            for(int i=1;i<rs.getMetaData().getColumnCount()+1;i++){
                System.out.println(rs.getString(i));
            }
        }

    }
}
