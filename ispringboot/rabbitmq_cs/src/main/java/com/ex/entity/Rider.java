package com.ex.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <p>
 *
 * </p>
 *
 * @author ttfor
 * @since 2021-04-18
 */
@Component
public class Rider  {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String orderNum;

    private String status;

    private String remark;


    public void setId(Integer id){
         this.id = id;
    }

    public Integer getId(){
         return this.id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
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
        return "Rider{" +
                "id=" + id +
                ", orderNum='" + orderNum + '\'' +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    @PostConstruct
    public void aaa(){
        System.out.println("##################################################");
    }
}
