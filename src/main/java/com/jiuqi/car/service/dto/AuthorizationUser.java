package com.jiuqi.car.service.dto;

import javax.validation.constraints.NotBlank;

/**
 * @author jie
 * @date 2018-11-30
 */
public class AuthorizationUser {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

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
}
