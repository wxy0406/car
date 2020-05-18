package com.jiuqi.car.service.dto;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author jie
 * @date 2018-11-23
 */

public class JwtUser {

    public JwtUser(String username, String avatar, String email, String phone, String dept, String job, boolean enabled, Timestamp createTime, List<String> roles) {
        this.username = username;
        this.avatar = avatar;
        this.email = email;
        this.phone = phone;
        this.dept = dept;
        this.job = job;
        this.enabled = enabled;
        this.createTime = createTime;
        this.roles = roles;
    }

    private String username;

    private String avatar;

    private String email;

    private String phone;

    private String dept;

    private String job;

    private boolean enabled;

    private Timestamp createTime;

    private List<String> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
