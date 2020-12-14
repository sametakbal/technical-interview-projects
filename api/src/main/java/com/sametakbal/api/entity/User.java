package com.sametakbal.api.entity;

import java.util.Date;

public class User {
    private int id;
    private String fullName;
    private String username;
    private String email;
    private String password;
    private boolean isAdmin;
    private Date createdAt;
    private Date lastLogin;

    public User() {
    }

    public User(int id, String fullName, String username, String email, String password, boolean isAdmin, Date createdAt, Date lastLogin) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
}
