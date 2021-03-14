package my.controller;


import my.domain.Permission;
import my.domain.Role;
import my.service.RoleService;
import my.util.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @RequestMapping("all")
    public ModelAndView findAll() throws Exception {
        List<Role> roleList = roleService.findAll();
        ModelAndView mv = new ModelAndView();
        mv.addObject("roleList", roleList);
        mv.setViewName("role_list");
        return mv;
    }
    @RequestMapping("/show")
    public ModelAndView findByRoleId(@RequestParam(name = "id",required = true) String id) throws Exception {
        Role role = roleService.findByRoleId(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("role", role);
        mv.setViewName("role_show");
        return mv;
    }
    @RequestMapping("/save")
    public String addRole(Role role) throws Exception {
        String roleName = role.getRoleName();
        if ("".equals(roleName) || roleName ==null){
            throw new MyException("角色名不能为空");
        }
        String rname = roleService.checkRoleNameExist(roleName);
        if(rname!=null){
            throw new MyException("角色名已经存在,请重新输入");
        }else{
            roleService.addRole(role);
        }
        return "forward:all";
    }
    @RequestMapping("/addPermission")
    public ModelAndView addPermission(@RequestParam(name = "id",required = true) String roleId){
        List<Permission> Permissions = roleService.findPermissionByRole(roleId);
        ModelAndView mv = new ModelAndView();
        mv.addObject("roleId", roleId);
        mv.addObject("Permissions", Permissions);
        mv.setViewName("role-permission-add");
        return mv;
    }
    @RequestMapping("addPermissionToRole")
    public String addPermissionToRole (String roleId,String[] ids) throws Exception {
        if(ids.length==0){
            return "forward:all";
        }
        roleService.addPermissionToRole(roleId,ids);
        return "forward:all";
    }

}
