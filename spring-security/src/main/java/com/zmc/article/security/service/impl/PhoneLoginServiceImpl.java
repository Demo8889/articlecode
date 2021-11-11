package com.zmc.article.security.service.impl;

import com.zmc.article.security.entity.TestUser;
import com.zmc.article.security.redis.PhoneCodeCaptchaRedis;
import com.zmc.article.security.service.PhoneLoginService;
import com.zmc.article.security.service.TestUserService;
import com.zmc.article.security.userdetails.UserCommonDetail;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: Demo
 * @date: 2021-10-29 11:20
 */
@Service
public class PhoneLoginServiceImpl implements PhoneLoginService {

    @Resource
    private PhoneCodeCaptchaRedis phoneCodeCaptchaRedis;

    //换成自己的
    @Resource
    private TestUserService testUserService;

    @Override
    public UserDetails loadUserByPhone(String phone, String code) {

        String cacheCode = phoneCodeCaptchaRedis.getPhoneCode(phone);
        if (StringUtils.isEmpty(cacheCode)) {
            throw new BadCredentialsException("手机验证码已过期，请重新获取！");
        }
        phoneCodeCaptchaRedis.deletePhoneCode(phone);
        if (!cacheCode.equals(code)) {
            throw new BadCredentialsException("手机验证码不正确！");
        }

        //换成自己的
        TestUser manager = testUserService.getByPhone(phone);
        if (manager == null) {
            throw new UsernameNotFoundException("用户不存在！");
        }

        Set<GrantedAuthority> authority = new HashSet();
        UserCommonDetail detail = new UserCommonDetail()
                .setUsername(manager.getUsername())
                .setPassword(manager.getPassword())
                .setAuthorities(authority)
                .setStatus(manager.isStatus())
                .setId(manager.getId());
        return detail;
    }
}
