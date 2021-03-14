package com.ex.ssm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * <p>
 *
 * </p>
 *
 * @author ttfor
 * @since 2021-03-11
 */
public class Member  {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    private String memberName;
    
    private String desc;
    
    private Integer uid;


    public void setId(Integer id){
         this.id = id;
    }

    public Integer getId(){
         return this.id;
    }


    public void setMemberName(String memberName){
         this.memberName = memberName;
    }

    public String getMemberName(){
         return this.memberName;
    }


    public void setDesc(String desc){
         this.desc = desc;
    }

    public String getDesc(){
         return this.desc;
    }


    public void setUid(Integer uid){
         this.uid = uid;
    }

    public Integer getUid(){
         return this.uid;
    }


    @Override
    public String toString() {
         return "Member{" +
         "id=" + id +
         ",memberName=" + memberName +
         ",desc=" + desc +
         ",uid=" + uid +
         "}";
    }
}