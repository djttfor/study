package com.ex.server.itest;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ex.server.dto.IAuth;
import com.ex.server.entity.*;
import com.ex.server.mapper.MenuMapper;
import com.ex.server.mapper.RoleMapper;
import com.ex.server.service.*;
import com.ex.server.util.JwtTokenUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class Test2 {
    @Autowired(required = false)
    MenuMapper menuMapper;

    @Autowired
    MenuService menuService;

    @Autowired
    AdminService adminService;

    @Autowired(required = false)
    RoleMapper roleMapper;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Autowired
    RoleService roleService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PositionService positionService;

    @Autowired
    AdminRoleService adminRoleService;

    @Autowired
    MailLogService mailLogService;


    @Test
    public void test1(){
        List<Menu> menuByAdminId = menuMapper.getMenuByAdminId(4);
        for (Menu menu : menuByAdminId) {
            System.out.println(menu);
        }
    }

    @Test
    public void test2(){
        System.out.println(roleMapper.getRoleByAdminId(1));
    }
    @Test
    public void test3(){
        Page<Admin> page = adminService.pageQuery(1, 5);
        List<Admin> records = page.getRecords();
        for (Admin record : records) {
            System.out.println(record);
        }
    }

    @Test
    public void test4(){
        List<Menu> menuByRoleId = menuMapper.getMenuByRoleId(2);
        for (Menu menu : menuByRoleId) {
            System.out.println(menu);
        }
        String usernameToken = jwtTokenUtil.generateUsernameToken(new User("admin", "123", new ArrayList<>()));
        Role role = new Role();
        role.setCode("ROLE_admin");
        IAuth iAuth = new IAuth(role,menuByRoleId);
        redisTemplate.opsForValue().set(usernameToken,iAuth,60L, TimeUnit.SECONDS);
    }

    @Test
    public void test5() throws JsonProcessingException {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImNyZWF0ZWQiOjE2MjEyMzQ3NjU0NjcsImV4cCI6MTYyMTgzOTU2NX0.-CKofgOtRz96m_RkH1Cz6I7XsZ_Ty-VvAl2cdIEP1IE";
        Object o = redisTemplate.opsForValue().get(token);
        if (o!=null){
            IAuth iAuth = (IAuth) o;
            System.out.println(iAuth);
        }
        System.out.println("o为null");
    }

    @Test
    public void test6(){
        List<Menu> menus = menuService.list(
                Wrappers.lambdaQuery(Menu.class)
                        .select(s -> "id".equals(s.getProperty()) ||
                                "parentId".equals(s.getProperty()) ||
                                "path".equals(s.getProperty()) ||
                                "name".equals(s.getProperty())
                        ));

        Menu menu = findChild(menus, menus.get(0));
        System.out.println(menu);
    }
    public Menu findChild(List<Menu> menus,Menu parentMenu){
        for (Menu menu : menus) {
            //匹配子节点
            if (menu.getParentId().equals(parentMenu.getId())) {
                //将子节点添加到父节点中
                if (parentMenu.getChildren()==null) {
                    parentMenu.setChildren(new ArrayList<>());
                }
                parentMenu.getChildren().add(menu);
                //子节点查找自己的子节点
                findChild(menus,menu);
            }
        }
        return parentMenu;
    }

    @Test
    public void test7() throws IOException {
        List<Employee> list = employeeService.list();
        Workbook exportExcel =
                ExcelExportUtil.exportExcel(new ExportParams("所有员工信息", "员工"),
                        Employee.class, list);
        FileOutputStream fileOutputStream = new FileOutputStream("F:\\Java\\Study\\IProject\\test.xls");
        exportExcel.write(fileOutputStream);
        fileOutputStream.close();
    }
    @Test
    public void test8() throws IOException {
        Admin admin = adminService.test10087();
        List<Admin> list = new ArrayList<>();
        list.add(admin);
        Workbook sheets =
                ExcelExportUtil.exportExcel(new ExportParams("4号用户", "用户"),
                        Admin.class,list );
        FileOutputStream fileOutputStream = new FileOutputStream("F:\\Java\\Study\\IProject\\test3.xls");
        sheets.write(fileOutputStream);
        fileOutputStream.close();

    }
    @Test
    public void test9(){
        mailLogService.update(new MailLog(),new LambdaUpdateWrapper<>(MailLog.class)
                .set(MailLog::getStatus,1).eq(MailLog::getMsgId,"bf0e25e7-062a-4e16-8e32-9025a9ab608a"));
//        mailLogService.update(new LambdaUpdateWrapper<>(MailLog.class)
//                .set(MailLog::getStatus,2).eq(MailLog::getMsgId,"bf0e25e7-062a-4e16-8e32-9025a9ab608a"));
    }
}
class B1{
    public static void main(String[] args) throws IOException, ParseException {

    }


}

