package com.ex.server.itest;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class C {
    @Excel(name = "cid",orderNum = "3")
    private Integer id;
    @Excel(name = "C的名字",orderNum = "2")
    private String name;
    private String address;

    public C() {
    }

    public C(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
