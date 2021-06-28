package com.ex.server.controller;


import com.ex.server.dto.ResponseBean;
import com.ex.server.entity.Menu;
import com.ex.server.service.MenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/sys/config/menu")
public class MenuController {
    @Autowired
    MenuService menuService;

    @ApiOperation(value = "根据用户id查询菜单列表")
    @GetMapping("/menu")
    public List<Menu> getMenuByAdminId(){
        return menuService.getMenuByAdminId();
    }

    @ApiOperation(value = "查询所有菜单")
    @GetMapping("/getAll")
    public ResponseBean getAllMenu(){
        return ResponseBean.success(menuService.getAllMenu());
    }
}

