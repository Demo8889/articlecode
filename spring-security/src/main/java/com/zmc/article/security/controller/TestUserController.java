package com.zmc.article.security.controller;

import com.zmc.article.security.entity.TestUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @author: Demo
 * @date: 2021-09-23 13:39
 */
@RestController
@RequestMapping(value = "test/user")
public class TestUserController {

    @GetMapping(value = "/list")
    public List<TestUser> list() {
        return Collections.emptyList();
    }

    @GetMapping(value = "/update")
    @PreAuthorize("hasRole('ADMIN')")
    public String update() {
        return "修改成功";
    }
}
