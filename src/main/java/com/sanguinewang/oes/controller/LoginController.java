package com.sanguinewang.oes.controller;

import com.sanguinewang.oes.annotation.UserLoginToken;
import com.sanguinewang.oes.component.MyToken;
import com.sanguinewang.oes.dataobject.User;
import com.sanguinewang.oes.enums.RoleEnums;
import com.sanguinewang.oes.services.TokenService;
import com.sanguinewang.oes.services.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

/**
 * @Description
 * @Author SanguineWang
 * @Date 2020-07-06 9:17
 */
@RestController
@RequestMapping("api/")
public class LoginController {

    @Value("${my.teacher}")
    private String roleTeacher;
    @Value("${my.student}")
    private String roleStudent;
    @Value("${my.admin}")
    private String roleAdmin;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    //登录
    @ApiOperation("登录，无需token鉴权")
    @PostMapping("login")
    public Map login(@RequestBody User user, HttpServletResponse response) {
        User userForBase = userService.findByUsername(user);
        if (userForBase == null) {
            return Map.of("message", "登录失败,用户不存在");
        } else {
            if (!userForBase.getPassword().equals(user.getPassword())) {
                return Map.of("message", "登录失败,密码错误");
            } else {

                String token = tokenService.getToken(userForBase);
                response.setHeader(MyToken.AUTHORIZATION, token);
                String roleVO = "";
                if (userForBase.getRole() == RoleEnums.ADMINISTRATOR)
                    roleVO = roleAdmin;
                else if (userForBase.getRole() == RoleEnums.STUDENT)
                    roleVO = roleStudent;
                else if (userForBase.getRole() == RoleEnums.TEACHER)
                    roleVO = roleTeacher;
                if (roleVO.length() == 0) throw new RuntimeException("角色确认失败");
                return Map.of("token", token, "role", roleVO);
            }
        }
    }

    @ApiOperation("用于测试是否登录成功")
    @UserLoginToken
    @GetMapping("loginTest")
    public String getMessage() {
        return "你已通过验证";
    }
}
