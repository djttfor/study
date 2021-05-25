package com.ex.server.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ex.server.dto.IAuth;
import com.ex.server.dto.ResponseBean;
import com.ex.server.entity.Admin;
import com.ex.server.entity.Role;
import com.ex.server.service.AdminService;
import com.ex.server.util.CommonUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ttfor
 * @since 2021-04-21
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @ApiOperation(value = "分页查询用户信息")
    @GetMapping("/page/{current}/{pageSize}")
    public ResponseBean pageQuery(
            @ApiParam("当前页")
            @PathVariable("current") int current,
            @ApiParam("当前页大小")
            @PathVariable("pageSize") int pageSize){
        return ResponseBean.success(adminService.pageQuery(current,pageSize));
    }

    @ApiOperation(value = "获取用户信息")
    @PostMapping("/info")
    public ResponseBean getAdminInfo(Principal principal,HttpServletRequest request){
        //获取令牌
        String token = CommonUtil.getUsernameTokenFromRequest(request);
        if (token == null){
            return ResponseBean.fail("未登录，请登录");
        }
        //通过令牌获取角色
        Object o = redisTemplate.opsForValue().get(token);
        if (o==null){
            return ResponseBean.fail("服务器出错，请联系管理员");
        }
        IAuth iAuth = (IAuth) o;

        if (null == principal){
            return ResponseBean.fail("未登录，请登录");
        }
        Admin admin = adminService.selectAdminByName(principal.getName());
        admin.setPassword(null);//防止密码泄露
        admin.setRoleCode(CommonUtil.getUnprefixedRoleName(iAuth.getRoleCode()));
        return ResponseBean.success(admin);
    }

    @ApiOperation(value = "查询所有用户信息，不分页")
    @GetMapping("/all")
    public ResponseBean getAll(){
        return ResponseBean.success(adminService.selectAll());
    }

    @ApiOperation(value = "启用或停用账户")
    @PostMapping("/switchStatus")
    public ResponseBean switchStatus(@RequestBody Map<String,Object> map){
        Object id = map.get("id");
        Object enabled = map.get("enabled");
        if(id==null || enabled==null){
            return ResponseBean.fail("更新状态失败");
        }
        boolean update = adminService.update(Wrappers.lambdaUpdate(Admin.class).set(Admin::isEnabled, enabled).eq(Admin::getId, id));
        if(!update){
            return ResponseBean.fail("更新状态失败");
        }
        return ResponseBean.success("更新状态成功");
    }

}

