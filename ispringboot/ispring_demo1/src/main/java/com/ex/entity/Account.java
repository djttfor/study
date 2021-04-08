package com.ex.entity;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author ttfor
 * @since 2021-04-05
 */
@Component
@Slf4j
public class Account implements InitializingBean {
    private Integer id;

    @Value("aaa")
    private String accountName;

    private String password;

    private Long balance;

    private Date createTime;

    private Integer uid;


    public void setId(Integer id){
         this.id = id;
    }

    public Integer getId(){
         return this.id;
    }


    public void setAccountName(String accountName){
         this.accountName = accountName;
    }

    public String getAccountName(){
         return this.accountName;
    }


    public void setPassword(String password){
         this.password = password;
    }

    public String getPassword(){
         return this.password;
    }


    public void setBalance(Long balance){
         this.balance = balance;
    }

    public Long getBalance(){
         return this.balance;
    }


    public void setCreateTime(Date createTime){
         this.createTime = createTime;
    }

    public Date getCreateTime(){
         return this.createTime;
    }


    public void setUid(Integer uid){
         this.uid = uid;
    }

    public Integer getUid(){
         return this.uid;
    }


    @Override
    public String toString() {
         return "Account{" +
         "id=" + id +
         ",accountName=" + accountName +
         ",password=" + password +
         ",balance=" + balance +
         ",createTime=" + createTime +
         ",uid=" + uid +
         "}";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("account:{}",this);
    }
}
