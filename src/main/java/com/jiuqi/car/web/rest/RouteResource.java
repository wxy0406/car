package com.jiuqi.car.web.rest;

import com.jiuqi.car.service.dto.AuthenticationInfo;
import com.jiuqi.car.service.dto.AuthorizationUser;
import com.jiuqi.car.service.dto.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RouteResource {

    private final Logger log = LoggerFactory.getLogger(RouteResource.class);

    private static final String ENTITY_NAME = "menu";

    /**
     * 构建前端路由所需要的菜单
     *
     * @return
     */
    @GetMapping(value = "/api/menus/build")
    public List<String> buildMenus() {
        return new ArrayList<>();
    }

    /**
     * 登录授权
     *
     * @param authorizationUser
     * @return
     */
    @PostMapping(value = "/auth/login")
    @CrossOrigin(origins = "http://localhost:8013")
    public AuthenticationInfo login(@Valid @RequestBody AuthorizationUser authorizationUser) {
        List<String> roles = new ArrayList<>();
        roles.add("ADMIN");
        JwtUser jwtUser = new JwtUser("admin", "https://i.loli.net/2019/04/04/5ca5b971e1548.jpeg", "admin@eladmin.net", "18888888888", "研发部", "全栈开发", true, new Timestamp(1534986716000L), roles);
        AuthenticationInfo authenticationInfo = new AuthenticationInfo("111", jwtUser);
        return authenticationInfo;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping(value = "/auth/info")
    public JwtUser getUserInfo() {
        List<String> roles = new ArrayList<>();
        roles.add("ADMIN");
        JwtUser jwtUser = new JwtUser("admin", "https://i.loli.net/2019/04/04/5ca5b971e1548.jpeg", "admin@eladmin.net", "18888888888", "研发部", "全栈开发", true, new Timestamp(1534986716000L), roles);
        return jwtUser;
    }
}
