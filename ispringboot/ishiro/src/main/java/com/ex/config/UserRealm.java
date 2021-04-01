package com.ex.config;

import com.ex.entity.User;
import com.ex.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Arrays;
@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;
    //用户授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        log.info("执行了授权功能");
//        //获取登录用户名
//        String name = (String) principalCollection.getPrimaryPrincipal();
//        //查询用户名称
//        User user = userService.selectByName(name);
        //这里的操作应该是根据用户名查询数据库该用户拥有的权限

        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        //添加角色与权限
        simpleAuthorizationInfo.addRole("root");
        simpleAuthorizationInfo.addStringPermissions(Arrays.asList("l1","l2","mod","que"));

        return simpleAuthorizationInfo;
    }


    //用户验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (!StringUtils.hasText((String) authenticationToken.getPrincipal())) {
            return null;
        }
        //获取用户信息
        String name = authenticationToken.getPrincipal().toString();
        //模拟查询数据库
        User user = userService.selectByName(name);
        if (user == null) {
            return null;
        } else {
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        }
    }
}
