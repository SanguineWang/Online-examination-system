package com.sanguinewang.oes.controller;

import com.sanguinewang.oes.VO.ResultVO;
import com.sanguinewang.oes.annotation.UserLoginToken;
import com.sanguinewang.oes.dataobject.*;
import com.sanguinewang.oes.enums.RoleEnums;
import com.sanguinewang.oes.service.AdminService;
import com.sanguinewang.oes.service.UserService;
import com.sanguinewang.oes.util.ResultVOUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author ChangChangChang123
 * @Date 2020-07-06 9:17
 */
@RestController
@RequestMapping("api/admin/")
public class AdminController {

    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AdminService adminService;

    @ApiOperation("进入在线考试后台,查看学生答题情况")
    @GetMapping("background")
    @UserLoginToken
    public ResultVO getBackgroundList(){
        return ResultVOUtil.success(
                Map.of("examList", List.of(new Exam()))
                , "进入后台成功");
    }

    @ApiOperation("获取用户信息列表")
    @GetMapping("user")
    @UserLoginToken
    public ResultVO getUserList(){
        return ResultVOUtil.success(
                Map.of("userList", adminService.getUserList())
                , "获取用户列表成功");
    }
    @ApiOperation("修改用户信息")
    @PatchMapping("user/{uid}")
    @UserLoginToken
    public ResultVO updateUser(@PathVariable int uid, @RequestBody User user){

        return ResultVOUtil.success(
                Map.of("userInfo", adminService.modifyUser(uid, user.getNumber(),user.getName(),user.getRole()))
                , "修改用户信息成功");
    }
    @ApiOperation("删除用户")
    @DeleteMapping("user/{uid}")
    @UserLoginToken
    public ResultVO deleteUser(@PathVariable int uid){
         adminService.deleteUser(uid);
        return ResultVOUtil.success(
                Map.of("myInfo",adminService.getUserList())
                , "删除用户信息成功");
    }
    @ApiOperation("添加教师")
    @PostMapping("user/teacher")
    @UserLoginToken
    public ResultVO addTeacher(Integer num,String name,String password){
        User user = new User();
        Teacher teacher = new Teacher();
        if(num!=null)
            user.setNumber(num);
        if(name!=null)
            user.setName(name);
        if(password!=null)
            user.setPassword(passwordEncoder.encode(password));
        user.setRole(RoleEnums.TEACHER);
        teacher.setUser(user);
        //administrator.setId(admin.getId());
        userService.addTeacher(user,teacher);
        return ResultVOUtil.success(
                Map.of("myInfo", teacher)
                , "添加用户成功");
    }
    @ApiOperation("添加学生")
    @PostMapping("user/student")
    @UserLoginToken
    public ResultVO addStudent(Integer num,String name,String password){
        User user = new User();
        Student student = new Student();
        if(num!=null)
            user.setNumber(num);
        if(name!=null)
            user.setName(name);
        if(password!=null)
            user.setPassword(passwordEncoder.encode(password));
        user.setRole(RoleEnums.STUDENT);
        student.setUser(user);
        //administrator.setId(admin.getId());
        userService.addStudent(user,student);
        return ResultVOUtil.success(
                Map.of("myInfo", student)
                , "添加用户成功");
    }
    @ApiOperation("添加管理员")
    @PostMapping("user/admin")
    @UserLoginToken
    public ResultVO addAdmin(Integer num,String name,String password){
        User user = new User();
        Administrator administrator = new Administrator();
        if(num!=null)
            user.setNumber(num);
        if(name!=null)
            user.setName(name);
        if(password!=null)
            user.setPassword(passwordEncoder.encode(password));
        user.setRole(RoleEnums.ADMINISTRATOR);
        administrator.setUser(user);
        //administrator.setId(admin.getId());
        userService.addAdministrator(administrator);
        return ResultVOUtil.success(
                Map.of("myInfo", administrator)
                , "添加用户成功");
    }
    @ApiOperation("按角色查询用户")
    @GetMapping("role/{role}")
    @UserLoginToken
    public ResultVO selectUserByRole(@PathVariable RoleEnums role){

        return ResultVOUtil.success(
                Map.of("myInfo",adminService.selectUserByCode(role) )
                , "查询用户成功");
    }
    @ApiOperation("按账号查询用户")
    @GetMapping("number/{number}")
    @UserLoginToken
    public ResultVO selectUserByNum(@PathVariable Integer number){

        User user = adminService.selectUserByNum(number);
        if(user ==null)
            ResultVOUtil.error(HttpStatus.NOT_FOUND,"查无此人");

        return ResultVOUtil.success(
                Map.of("myInfo", user)
                , "查询用户成功");
    }

}
