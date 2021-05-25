package com.ex.server.controller;


import com.ex.server.dto.ResponseBean;
import com.ex.server.dto.RoleBean;
import com.ex.server.entity.Menu;
import com.ex.server.service.MenuRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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
@RequestMapping("/sys/config/perm")
public class MenuRoleController {
    @Autowired
    MenuRoleService menuRoleService;

    @ApiOperation(value = "更新角色权限")
    @PostMapping("/updatePermission")
    public ResponseBean setMenuRoleId(@RequestBody RoleBean roleBean){
        if(!menuRoleService.setMenuRoleId(roleBean)){
            return ResponseBean.fail("权限更新失败");
        }
        return ResponseBean.success("权限更新成功");
    }


}

