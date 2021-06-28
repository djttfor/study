package com.ex.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class OrderRy {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String OrderNum;
    private String status;

    public OrderRy(){}

    public OrderRy(String orderNum, String status) {
        OrderNum = orderNum;
        this.status = status;
    }
}
