package com.ex.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * <p>
 *
 * </p>
 *
 * @author ttfor
 * @since 2021-04-18
 */
public class IOrder {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String orderNum;

    private Integer userId;

    private String status;

    private String remark;


    public void setId(Integer id){
         this.id = id;
    }

    public Integer getId(){
         return this.id;
    }


    public void setOrderNum(String orderNum){
         this.orderNum = orderNum;
    }

    public String getOrderNum(){
         return this.orderNum;
    }


    public void setUserId(Integer userId){
         this.userId = userId;
    }

    public Integer getUserId(){
         return this.userId;
    }


    public void setStatus(String status){
         this.status = status;
    }

    public String getStatus(){
         return this.status;
    }


    public void setRemark(String remark){
         this.remark = remark;
    }

    public String getRemark(){
         return this.remark;
    }


    @Override
    public String toString() {
         return "Order{" +
         "id=" + id +
         ",orderNum=" + orderNum +
         ",userId=" + userId +
         ",status=" + status +
         ",remark=" + remark +
         "}";
    }
}
