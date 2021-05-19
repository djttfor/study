package com.ex.server.controller;


import com.ex.server.dto.ResponseBean;
import com.ex.server.service.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation("查询所有角色")
    @GetMapping("/getMidByRid/{rid}")
    public ResponseBean getMenuIdByRid(@PathVariable("rid") Integer rid){
        return ResponseBean.success(roleService.getMenuIdByRid(rid));
    }
}

