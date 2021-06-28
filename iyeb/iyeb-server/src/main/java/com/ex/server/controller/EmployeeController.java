package com.ex.server.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.ex.server.dto.ResponseBean;
import com.ex.server.entity.Employee;
import com.ex.server.service.EmployeeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ttfor
 * @since 2021-04-21
 */
@RestController
@RequestMapping("/emp/basic")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @ApiOperation(value = "分页查询员工信息")
    @GetMapping("/page/{current}/{pageSize}")
    public ResponseBean pageQuery(
            @ApiParam("当前页")
            @PathVariable("current") int current,
            @ApiParam("当前页大小")
            @PathVariable("pageSize") int pageSize){
        return ResponseBean.success(employeeService.pageQuery(current,pageSize));
    }

    @ApiOperation(value = "导出所有员工信息")
    @GetMapping(value = "/export",produces = "application/octet-stream")
    public void export(HttpServletResponse response){

        ExportParams params = new ExportParams("员工表","员工表", ExcelType.HSSF);
        //查询所有数据
        List<Employee> list = employeeService.list();
        Workbook sheets = ExcelExportUtil.exportExcel(params, Employee.class, list);

        ServletOutputStream outputStream = null;
        //设置文件以附件形式下载
        try {
            //设置响应格式以流的形式响应
            response.setHeader("Content-Type","application/octet-stream");
            //设置下载格式以附件的形式
            response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode("员工表.xls","UTF-8"));
            outputStream = response.getOutputStream();
            sheets.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @ApiOperation(value = "导入员工信息")
    @PostMapping(value = "/import")
    public ResponseBean importEmployee(MultipartFile file){
        ImportParams importParams = new ImportParams();
        importParams.setHeadRows(1);
        importParams.setTitleRows(1);

        try {
            List<Object> objects = ExcelImportUtil.importExcel(file.getInputStream(), Employee.class, importParams);
            List<Employee> employees = new ArrayList<>();
            if(objects==null || objects.size()==0){
                return ResponseBean.success("没有数据可导入");
            }
            for (Object object : objects) {
                Employee employee = (Employee) object;
                employees.add(employee);
            }
            if (!employeeService.saveBatch(employees)) {
                return ResponseBean.success("导入失败");
            }

        } catch (Exception e) {
            return ResponseBean.success("导入文件格式异常，请检查");
        }

        return ResponseBean.success("导入成功");
    }
    @ApiOperation(value = "添加员工(懒得做)，只是模拟发送邮件")
    @PostMapping(value = "/add")
    public ResponseBean addEmployee(){
        Employee employee = new Employee();
        employee.setId(10086);
        employee.setEmail("djttfor@163.com");
        employee.setName("jimmy");
        employee.setGender("女");
        employee.setDepartmentId(2);
        employee.setJobLevelId(1);
        boolean flag = employeeService.addEmployee(employee);
        if(!flag){
            return ResponseBean.fail("添加员工失败，请联系管理员");
        }
        return ResponseBean.success("添加员工成功");
    }
}

