package com.ex.server.itest;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class D {
    @Excel(name = "did")
    private Integer id;
    @Excel(name = "D的名字")
    private String name;

    public D() {
    }

    public D(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
