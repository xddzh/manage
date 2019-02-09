package com.warehouse.manage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class AccountInfo extends BaseEntity {
    private String username;
    private String password;
    private String name;
    private int type;
    private int job;
    private String jobName;
    private boolean isEnable;
    private List<AccountRole> authorities;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getJob() {
        return job;
    }

    public void setJob(int job) {
        this.job = job;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public List<AccountRole> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<AccountRole> authorities) {
        this.authorities = authorities;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
