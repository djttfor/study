package com.ex.server.itest;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import lombok.Data;

import java.util.List;

@Data
public class A {
    @Excel(name = "aid",needMerge = true)
    private Integer id;
    @Excel(name = "A的名字",needMerge = true)
    private String name;
    private String remark;
    @ExcelEntity
    private B b;
    @ExcelCollection(name="集合C",orderNum = "4")
    private List<C> cs;
    @ExcelCollection(name = "集合D",orderNum = "3")
    private List<D> ds;

}
