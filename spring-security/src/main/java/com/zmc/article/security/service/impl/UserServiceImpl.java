package com.zmc.article.security.service.impl;

import com.zmc.article.security.entity.TestUser;
import com.zmc.article.security.service.TestUserService;
import com.zmc.article.security.userdetails.UserCommonDetail;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: Demo
 * @date: 2021-09-22 17:22
 */
@Service
public class UserServiceImpl implements UserDetailsService {

    //换成自己的
    @Resource
    private TestUserService testUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //换成自己的
        TestUser manager = testUserService.getByLoginName(username);
        if (manager == null) {
            throw new UsernameNotFoundException("用户不存在！");
        }

        Set<GrantedAuthority> authority = new HashSet();
        UserCommonDetail detail = new UserCommonDetail()
                .setUsername(username)
                .setPassword(manager.getPassword())
                .setAuthorities(authority)
                .setStatus(manager.isStatus())
                .setId(manager.getId());
        return detail;
    }
}
