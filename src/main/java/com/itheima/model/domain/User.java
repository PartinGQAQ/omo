package com.itheima.model.domain;

import java.util.Date;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private Date created;
    private int valid;
    private int level;
    private int ex_point;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getEx_point() {
        return ex_point;
    }

    public void setEx_point(int ex_point) {
        this.ex_point = ex_point;
    }

    public User(String username, String password, String email, Date created, int valid, int level, int ex_poind) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.created = created;
        this.valid = valid;
        this.level = level;
        this.ex_point = ex_poind;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", created=" + created +
                ", valid=" + valid +
                ", level=" + level +
                ", ex_point=" + ex_point +
                '}';
    }
}
