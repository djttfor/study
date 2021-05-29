package com.ex.server.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ex.server.constant.IConstant;
import com.ex.server.dto.IAuth;
import com.ex.server.dto.LoginParam;
import com.ex.server.dto.ResponseBean;
import com.ex.server.entity.Admin;
import com.ex.server.entity.Menu;
import com.ex.server.entity.Role;
import com.ex.server.mapper.AdminMapper;
import com.ex.server.service.AdminService;
import com.ex.server.service.IBaseService;
import com.ex.server.service.MenuService;
import com.ex.server.service.RoleService;
import com.ex.server.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ttfor
 * @since 2021-04-21
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService , IBaseService<Admin> {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    String tokenHead;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Autowired
    RoleService roleService;

    @Autowired
    MenuService menuService;

    @Autowired
    AdminMapper adminMapper;


    @Override
    @Transactional
    public ResponseBean login(LoginParam loginParam, HttpServletRequest request, HttpServletResponse response) {
        if (
                null==loginParam.getUsername()
                ||
                        "".equals(loginParam.getUsername())
                ||
                        null ==loginParam.getPassword()
                ||
                        "".equals(loginParam.getPassword())
                ||
                        null==loginParam.getCode()
                ||
                        "".equals(loginParam.getCode())
                ||
                        null==loginParam.getCodeToken()
                ||
                        "".equals(loginParam.getCodeToken())
        ) {
            return ResponseBean.fail("用户名或者密码或验证码不能为空");
        }

        String code = (String) redisTemplate.opsForValue().get(loginParam.getCodeToken());
        if (code==null){
            return ResponseBean.fail("验证码已过期，请重新获取");
        }
        if (!loginParam.getCode().equalsIgnoreCase(code)){
            return ResponseBean.fail("验证码错误，请重新输入");
        }

        //使用springSecurity验证
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginParam.getUsername());
        if (userDetails==null || !passwordEncoder.matches(loginParam.getPassword(),userDetails.getPassword())){
            return ResponseBean.fail("用户名或密码不正确");
        }

        if (!userDetails.isEnabled()) {
            return ResponseBean.fail("账号未启用，请联系管理员");
        }

        if (!userDetails.isAccountNonExpired()) {
            return ResponseBean.fail("账号已过期，请联系管理员");
        }

        if (!userDetails.isAccountNonLocked()) {
            return ResponseBean.fail("账号已锁定，请联系管理员");
        }

        //获取用户权限
        Admin userVerifyDetails = (Admin) userDetails;
        //获取角色
        Role role = roleService.getRoleByAdminId(userVerifyDetails.getId());
        //获取权限路径
        List<Menu> menus = menuService.getMenuByRoleId(role.getId());
        //构建认证对象
        IAuth iAuth = new IAuth(role,menus);

        //生成令牌
        String usernameByToken = jwtTokenUtil.generateUsernameToken(userDetails);

        //将角色与权限存入redis,过期时间7天
        redisTemplate.opsForValue().set(usernameByToken,iAuth, IConstant.AUTH_EXPIRATION_TIME, TimeUnit.SECONDS);

        Map<String,String> map = new HashMap<>();
        map.put("token",usernameByToken);//令牌
        map.put("tokenHead",tokenHead);//令牌前缀

        //更新springSecurity对象，将userDetails，用户权限放入上下文中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails,null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        return ResponseBean.success("登录成功",map);
    }

    @Override
    public Admin selectAdminByName(String name) {
        return getOne(lqw()
                .eq(Admin::getName,name));
    }

    @Override
    public List<Admin> queryAll() {
        return list();
    }

    @Override
    public Page<Admin> pageQuery(int current, int pageSize) {
        Page<Admin> page = new Page<>(current,pageSize);
        return page(page,
                Wrappers.lambdaQuery(Admin.class).select(column -> !column.getProperty().equals("password")));
    }

    /**
     * 测试方法
     */
    @Override
    @Transactional
    public void test() {
        Admin admin = new Admin();
        admin.setName("King");
        admin.setUsername("King");
        admin.setAddress("广州市");
       // admin.setPassword("123");
        //admin.setAge(18);
        admin.setEnabled(true);
        //admin.setGmtCreate(LocalDateTime.now());
        //admin.setTelephone("10086");
        save(admin);
    }

    @Override
    public List<Admin> selectAll() {
        return adminMapper.selectAll();
    }

    @Override
    /**
     * 测试方法
     */
    public Admin test10087() {
        return adminMapper.test10087();
    }


    @Override
    public Class<Admin> buildEntity() {
        return Admin.class;
    }
}
