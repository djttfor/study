package com.ex.server.security;

import com.ex.server.constant.IConstant;
import com.ex.server.dto.IAuth;
import com.ex.server.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;

@Slf4j
public class AccessDecisionManagerImpl implements AccessDecisionManager {
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        //匿名模式不验证
        if(authentication instanceof AnonymousAuthenticationToken){
            throw new AccessDeniedException("请登录");
        }
        FilterInvocation filterInvocation = (FilterInvocation) object;
        //swagger2请求不验证
        String referer = filterInvocation.getRequest().getHeader("Referer");
        if(referer!=null && referer.endsWith(IConstant.SWAGGER_REFERER)){
            return;
        }

        //获取令牌
        String token = CommonUtil.getUsernameTokenFromRequest(filterInvocation.getRequest());
        if (token==null) {
            throw new AccessDeniedException("登录过期，请重新登录");
        }
        Object o = redisTemplate.opsForValue().get(token);
        if (o==null) {
            throw new AccessDeniedException("登录过期，请重新登录");
        }
        //获取请求路径
        String requestUrl = filterInvocation.getRequestUrl();
        //不需要验证的路径
        if (requestUrl.startsWith("/admin/")) {
            return;
        }
        //验证权限
        IAuth iAuth = (IAuth) o;
        if (iAuth.getAuthPath().stream().noneMatch(requestUrl::startsWith)){
            throw new AccessDeniedException("权限不足，请联系管理员");
        }
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
