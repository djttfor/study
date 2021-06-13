package com.ex.server.controller;


import com.ex.server.dto.ResponseBean;
import com.ex.server.entity.Department;
import com.ex.server.service.DepartmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/sys/config/dept")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @ApiOperation("查询所有部门，按照父子部门排列好")
    @GetMapping("/getDeptDetail")
    public ResponseBean getDeptDetail(){
        ResponseBean success = null;
            Department department = departmentService.selectAll();
            success = ResponseBean.success(department);
        return success;
    }

    @ApiOperation("查询所有部门，不排列")
    @GetMapping("/getAll")
    public ResponseBean getAll(){
        return ResponseBean.success(departmentService.list());
    }

    @ApiOperation("更新部门信息")
    @PostMapping("/update")
    public ResponseBean updateDept(@RequestBody Department department){
        if (!departmentService.updateById(department)) {
            return ResponseBean.success("更新部门失败");
        }
        return ResponseBean.success("更新部门成功");
    }

    @ApiOperation("根据id集合删除部门")
    @PostMapping("/delete")
    public ResponseBean deleteDept(@RequestBody List<Integer> ids){
        if (!departmentService.removeByIds(ids)) {
            return ResponseBean.success("删除部门失败");
        }
        return ResponseBean.success("删除部门成功");
    }

    @ApiOperation("添加部门")
    @PostMapping("/add")
    public ResponseBean addDept(@RequestBody Department department){
        if (!departmentService.save(department)) {
            return ResponseBean.success("添加部门失败");
        }
        return ResponseBean.success("添加部门成功");
    }


}

