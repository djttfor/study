package com.ex.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class IUserDetailServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (!"root".equals(s)) {
            throw new UsernameNotFoundException("用户名不是root");
        }
        return new User("root",new BCryptPasswordEncoder().encode("123"),
                AuthorityUtils.commaSeparatedStringToAuthorityList("v1,v2"));
    }
}
