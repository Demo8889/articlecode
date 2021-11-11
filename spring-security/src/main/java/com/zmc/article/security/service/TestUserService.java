package com.zmc.article.security.service;

import com.zmc.article.security.entity.TestUser;

/**
 * @author: Demo
 * @date: 2021-09-23 10:09
 */
public interface TestUserService {

    TestUser getByLoginName(String username);

    TestUser getByPhone(String phone);
}
