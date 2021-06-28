package com.ex.ssm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ttfor
 * @since 2021-03-11
 */
public class User  {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String phone;

    private String address;

    @TableField(exist = false)
    private Account account;
    @TableField(exist = false)
    private List<Account> accountList;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", account=" + account +
                ", accountList=" + accountList +
                '}';
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public void setId(Integer id){
         this.id = id;
    }

    public Integer getId(){
         return this.id;
    }


    public void setUsername(String username){
         this.username = username;
    }

    public String getUsername(){
         return this.username;
    }


    public void setPassword(String password){
         this.password = password;
    }

    public String getPassword(){
         return this.password;
    }


    public void setPhone(String phone){
         this.phone = phone;
    }

    public String getPhone(){
         return this.phone;
    }


    public void setAddress(String address){
         this.address = address;
    }

    public String getAddress(){
         return this.address;
    }


}
