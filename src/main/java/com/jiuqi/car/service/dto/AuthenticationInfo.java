package com.jiuqi.car.service.dto;

/**
 * @author jie
 * @date 2018-11-23
 * 返回token
 */
public class AuthenticationInfo {

    public AuthenticationInfo(String token, JwtUser user) {
        this.token = token;
        this.user = user;
    }

    private String token;

    private JwtUser user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public JwtUser getUser() {
        return user;
    }

    public void setUser(JwtUser user) {
        this.user = user;
    }
}
