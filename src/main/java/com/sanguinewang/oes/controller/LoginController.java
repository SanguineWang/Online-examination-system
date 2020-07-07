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
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

/**
 * @Description
 * @Author SanguineWang Rice
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

    private String errorLoginMsg = "账号或密码错误";
    private String nullLoginMsg = "账号或密码不能为空";
    private String successLoginMsg = "登录成功";
    private String severErrorMsg = "服务器或网络出现异常";

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
        User userFromDB = Optional.ofNullable(userService.findUserbyNumber(user.getNumber()))
                .filter(user1 -> encoder.matches(user.getPassword(), user1.getPassword()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, errorLoginMsg));

        String token = tokenService.getToken(userFromDB);
        response.setHeader(MyToken.AUTHORIZATION, token);
        String roleVO = "";
        RoleEnums role = userFromDB.getRole();
        if (role == RoleEnums.ADMINISTRATOR)
            roleVO = roleAdmin;
        else if (role == RoleEnums.STUDENT)
            roleVO = roleStudent;
        else if (role == RoleEnums.TEACHER)
            roleVO = roleTeacher;
        if (roleVO.length() == 0) {
            ResultVOUtil.error(HttpStatus.UNAUTHORIZED, "该角色没有处理");
        }
        return ResultVOUtil.success(Map.of("token", token, "role", roleVO),
                successLoginMsg);

    }


    @ApiOperation("重置密码,传入新的密码")
    @UserLoginToken
    @PostMapping("password")
    public void resetPwd(@RequestBody User reset_user) {
        if (reset_user.getPassword().length() == 0) {
            ResultVOUtil.error(HttpStatus.BAD_REQUEST, nullLoginMsg);
        }
        //通过用户的number找到指定用户
        User userFromDB = Optional.ofNullable(userService.findUserbyNumber(reset_user.getNumber()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, errorLoginMsg));
        try {
            //对读入的新密码进行编码并放入
            String newPassword = encoder.encode(reset_user.getPassword());
            userFromDB.setPassword(newPassword);
            userService.save(userFromDB);
        } catch (Exception e) {
            ResultVOUtil.error(HttpStatus.SERVICE_UNAVAILABLE, severErrorMsg);
        }

    }

    @ApiOperation("用于测试是否登录成功")
    @UserLoginToken
    @GetMapping("loginTest")
    public ResultVO getMessage(HttpServletRequest request) {
        return ResultVOUtil.success(
                Map.of("role", requestComponent.getRole(), "uid", requestComponent.getUid())
                , successLoginMsg);

    }
}
