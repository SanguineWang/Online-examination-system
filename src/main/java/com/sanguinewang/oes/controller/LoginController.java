package com.sanguinewang.oes.controller;

import com.sanguinewang.oes.VO.ResultVO;
import com.sanguinewang.oes.annotation.UserLoginToken;
import com.sanguinewang.oes.component.MyToken;
import com.sanguinewang.oes.component.RequestComponent;
import com.sanguinewang.oes.dataobject.User;
import com.sanguinewang.oes.enums.RoleEnums;
import com.sanguinewang.oes.services.TokenService;
import com.sanguinewang.oes.services.UserService;
import com.sanguinewang.oes.util.ResultVOUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Description
 * @Author SanguineWang
 * @Date 2020-07-06 9:17
 */
@RestController
@Slf4j
@RequestMapping("api/")
public class LoginController {

    @Value("${my.teacher}")
    private String roleTeacher;
    @Value("${my.student}")
    private String roleStudent;
    @Value("${my.admin}")
    private String roleAdmin;

    @Autowired
    RequestComponent requestComponent;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    //登录
    @ApiOperation("登录，无需token鉴权")
    @PostMapping("login")
    public ResultVO login(@RequestBody User user, HttpServletResponse response) {
        if (user == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "用户名或密码错误");
        if (user.getPassword() == null || user.getNumber() == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "用户名或密码不能为空");

        User userFromDB = userService.findUserbyNumber(user.getNumber());

        if (userFromDB == null) {
            return ResultVOUtil.error("登录失败,用户不存在");
        } else {
            if (!encoder.matches(user.getPassword(), userFromDB.getPassword())) {
                return ResultVOUtil.error("登录失败,密码错误");
            } else {

                String token = tokenService.getToken(userFromDB);
                response.setHeader(MyToken.AUTHORIZATION, token);
                String roleVO = "";
                if (userFromDB.getRole() == RoleEnums.ADMINISTRATOR)
                    roleVO = roleAdmin;
                else if (userFromDB.getRole() == RoleEnums.STUDENT)
                    roleVO = roleStudent;
                else if (userFromDB.getRole() == RoleEnums.TEACHER)
                    roleVO = roleTeacher;
                if (roleVO.length() == 0) throw new RuntimeException("角色确认失败");
                return ResultVOUtil.success(Map.of("token", token, "role", roleVO));
            }
        }
    }

    @ApiOperation("用于测试是否登录成功")
    @UserLoginToken
    @GetMapping("loginTest")
    public ResultVO getMessage(HttpServletRequest request) {
        return ResultVOUtil.success(
                Map.of("massage", "登录成功", "role", requestComponent.getRole(), "uid", requestComponent.getUid())
        );

    }
}
