package com.zmc.article.security.entity;

/**
 * @author: Demo
 * @date: 2021-09-23 10:21
 */
public class TestUser {

    private long id;

    private String username;

    private String password;

    /**
     * 冻结状态
     */
    private boolean status;

    public long getId() {
        return id;
    }

    public TestUser setId(long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public TestUser setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public TestUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isStatus() {
        return status;
    }

    public TestUser setStatus(boolean status) {
        this.status = status;
        return this;
    }
}
