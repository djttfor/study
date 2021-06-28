package com.ex.server.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ex.server.dto.AdminRoleBean;
import com.ex.server.dto.ResponseBean;
import com.ex.server.entity.AdminRole;
import com.ex.server.service.AdminRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
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
@RequestMapping("/admin/role")
public class AdminRoleController {

    @Autowired
    AdminRoleService adminRoleService;


    @ApiOperation(value = "删除用户与角色的关联")
    @GetMapping("/delete/{aid}/{rid}")
    public ResponseBean deleteById(@PathVariable("aid") Integer aid,
                                   @PathVariable("rid") Integer rid){
        boolean remove = adminRoleService.remove(Wrappers.lambdaUpdate(AdminRole.class)
                .eq(AdminRole::getAid, aid).eq(AdminRole::getRid, rid));
        if(!remove){
            return ResponseBean.fail("删除角色失败");
        }
        return ResponseBean.success("删除角色成功");
    }

    @ApiOperation(value = "添加用户与角色的关联")
    @PostMapping("/addRelation")
    public ResponseBean deleteById(@RequestBody AdminRoleBean adminRoleBean){
        if(!(adminRoleBean.getRoleIds()!=null && adminRoleBean.getRoleIds().size()>0
                && adminRoleBean.getAid()!=null)){
            return ResponseBean.fail("请选择要关联的角色");
        }
        List<AdminRole> adminRoleList = new ArrayList<>();
        for (Integer roleId : adminRoleBean.getRoleIds()) {
            AdminRole adminRole = new AdminRole();
            adminRole.setAid(adminRoleBean.getAid());
            adminRole.setRid(roleId);
            adminRole.setGmtCreate(LocalDateTime.now());
            adminRole.setRemark("这是批量添加的");
            adminRoleList.add(adminRole);
        }
        if(!adminRoleService.saveBatch(adminRoleList)){
            return ResponseBean.fail("添加角色失败");
        }
        return ResponseBean.success("添加角色成功");
    }
}

