package com.ex.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.sql.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author ttfor
 * @since 2021-04-05
 */
public class Account  {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
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
}