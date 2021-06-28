package com.ex.server.controller;


import com.ex.server.dto.ResponseBean;
import com.ex.server.dto.RoleBean;
import com.ex.server.service.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ttfor
 * @since 2021-04-21
 */
@RestController
@RequestMapping("/sys/config/role")
public class RoleController {

    @Autowired
    RoleService roleService;


    @ApiOperation("查询所有角色")
    @GetMapping("/getAll")
    public ResponseBean getAll(){
        return ResponseBean.success(roleService.list());
    }

    @ApiOperation("根据角色id查询该角色所有权限路径")
    @GetMapping("/getMidByRid/{rid}")
    public ResponseBean getMenuIdByRid(@PathVariable("rid") Integer rid){
        return ResponseBean.success(roleService.getMenuIdByRid(rid));
    }

    @ApiOperation("根据id删除角色")
    @GetMapping("/del/{id}")
    public ResponseBean deleteRole(@PathVariable("id") Integer id){
        if (!roleService.deleteById(id)) {
            return ResponseBean.fail("删除失败");
        }
        return ResponseBean.success("删除成功");
    }

    @ApiOperation("添加新角色")
    @PostMapping("/add")
    public ResponseBean addRole(@RequestBody RoleBean roleBean){
        if (!roleService.addRole(roleBean)) {
            return ResponseBean.fail("添加角色失败");
        }
        return ResponseBean.success("添加角色成功");
    }

}

