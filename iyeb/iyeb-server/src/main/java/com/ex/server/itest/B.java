package com.ex.server.itest;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class B {
    @Excel(name = "bid",needMerge = true)
    private Integer id;
    @Excel(name = "B的名字",needMerge = true,orderNum = "2")
    private String name;
    private Integer age;
}
