package com.zmc.article.security.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author: Demo
 * @date: 2021-10-29 11:19
 */
public interface PhoneLoginService {

    UserDetails loadUserByPhone(String phone, String code);
}
