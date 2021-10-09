package com.zmc.article.security.service.impl;

import com.zmc.article.security.entity.TestUser;
import com.zmc.article.security.service.TestUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: Demo
 * @date: 2021-09-23 10:10
 */
@Service
public class TestUserServiceImpl implements TestUserService {

    /**
     * 此类实际上是从数据库中查询用户，当前为示例
     */
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public TestUser getByLoginName(String username) {
        return new TestUser().setId(9527).setUsername("Demo").setPassword(passwordEncoder.encode("123456")).setStatus(true);
    }
}
