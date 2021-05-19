package com.ex.server.controller;


import com.ex.server.dto.IAuth;
import com.ex.server.dto.ResponseBean;
import com.ex.server.entity.Admin;
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

    @GetMapping("/all")
    public List<Admin> queryAll(){
        return adminService.queryAll();
    }

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

}

