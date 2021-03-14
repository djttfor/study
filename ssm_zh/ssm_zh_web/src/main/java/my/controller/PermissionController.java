package my.controller;

import my.domain.Permission;
import my.service.PermissionService;
import my.util.controller.utils.MyUtil;
import my.util.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    PermissionService permissionService;
    @Autowired
    MyUtil<Permission> myUtil;
    @RequestMapping("/all")
    @RolesAllowed("ADMIN")
    public ModelAndView findAll(@RequestParam(name = "pageNum",required = true,defaultValue = "1") int pageNum,
                                @RequestParam(name = "pageSize",required = true,defaultValue = "4") int pageSize) throws Exception {
        List<Permission> permissions = permissionService.findAll(pageNum,pageSize);

        return myUtil.findAllPaging(permissions, "permission_list");
    }
    @RequestMapping("/save")
    @RolesAllowed("ADMIN")
    public String addPermission(Permission permission) throws Exception {
        String url = permission.getUrl();
        if("".equals(url) || url ==null){
            throw new MyException("资源路径不能为空");
        }
        if(permissionService.checkUrlExist(url)!=null){
            throw new MyException("资源路径已存在");
        }else{
            permissionService.addPermission(permission);
        }
        return "forward:all";
    }

    /**
     * 查看权限详情
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/show")
    @RolesAllowed("ADMIN")
    public ModelAndView showPermission(@RequestParam(name = "id",required = true) String id) throws Exception {
        Permission permission = permissionService.findPermissionByPid(id);
        return myUtil.show(permission, "permission", "permission_show");
    }


}
