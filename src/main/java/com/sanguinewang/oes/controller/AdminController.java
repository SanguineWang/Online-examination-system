package com.sanguinewang.oes.controller;

import com.sanguinewang.oes.VO.ResultVO;
import com.sanguinewang.oes.annotation.UserLoginToken;
import com.sanguinewang.oes.dataobject.*;
import com.sanguinewang.oes.enums.RoleEnums;
import com.sanguinewang.oes.repository.StudentRepository;
import com.sanguinewang.oes.service.AdminService;
import com.sanguinewang.oes.service.StudentService;
import com.sanguinewang.oes.service.UserService;
import com.sanguinewang.oes.util.ResultVOUtil;
import io.swagger.annotations.ApiOperation;
import javassist.expr.NewArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author ChangChangChang123
 * @Date 2020-07-06 9:17
 */
@RestController
@RequestMapping("api/admin/")
@Slf4j
public class AdminController {

    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AdminService adminService;

    @ApiOperation("获取学生信息列表")
    @GetMapping("students")
    @UserLoginToken
    public ResultVO getStudentList() {
        return ResultVOUtil.success(
                Map.of("studentList", adminService.getStudentList())
                , "获取学生列表成功");
    }

    @ApiOperation("获取教师信息列表")
    @GetMapping("teachers")
    @UserLoginToken
    public ResultVO getTeacherList() {
        return ResultVOUtil.success(
                Map.of("teacherList", adminService.getTeacherList())
                , "获取教师列表成功");
    }


    @ApiOperation("删除学生")
    @DeleteMapping("students/{sid}")
    @UserLoginToken
    public ResultVO deleteStudent(@PathVariable Integer sid) {
        adminService.deleteUser(sid);
        return ResultVOUtil.success(
                Map.of("studentList", adminService.getStudentList())
                , "删除学生信息成功");
    }

    @ApiOperation("删除教师")
    @DeleteMapping("teachers/{uid}")
    @UserLoginToken
    public ResultVO deleteTeacher(@PathVariable int uid) {
        adminService.deleteUser(uid);
        return ResultVOUtil.success(
                Map.of("teacherList", adminService.getTeacherList())
                , "删除教师信息成功");
    }

    @ApiOperation("添加教师")
    @PostMapping("teachers")
    @UserLoginToken
    public ResultVO addTeacher(@RequestBody Teacher teacher) {

        userService.addTeacher(teacher);
        return ResultVOUtil.success(
                Map.of("teacherList", adminService.getTeacherList())
                , "获取教师列表成功");
    }

    @ApiOperation("添加学生")
    @PostMapping("students")
    @UserLoginToken
    public ResultVO addStudent(@RequestBody Student student) {
        log.debug("{}", student);
        userService.addStudent(student);
        return ResultVOUtil.success(
                Map.of("studentList", adminService.getStudentList())
                , "获取学生列表成功");
    }

    //    /*
//    * 模糊查询
//    */
//    @ApiOperation("按姓名查询老师")
//    @GetMapping("teachers/{name}")
//    @UserLoginToken
//    public ResultVO findtUser(@PathVariable String name){
//        adminService.findTeacherByname(name);
//        return  ;
//    }
//    /*
//     * 模糊查询
//     */
//    @ApiOperation("按姓名查询学生")
//    @GetMapping("teachers/{name}")
//    @UserLoginToken
//    public ResultVO findtUser(@PathVariable String name){
//
//        return List<Student> ;
//    }
//    @ApiOperation("修改用户信息")
//    @PatchMapping("user/{uid}")
//    @UserLoginToken
//    public ResultVO updateUser(@RequestBody User user){
//
//        return ResultVOUtil.success(
//                Map.of("userInfo", adminService.modifyUser(uid, user.getNumber(),user.getName(),user.getRole()))
//                , "修改用户信息成功");
//    }
//    @ApiOperation("按姓名查询用户")
//    @GetMapping("userName")
//    @UserLoginToken
//    @ResponseBody
//    public ResultVO selectUserByNum(HttpServletRequest req) {
//
//        String likeName = req.getParameter("name");
//        List<User> list = null;
//        //这里需要用到模糊查询的通配符
//        list = adminService.selectUserByNum("%" + likeName + "%");
//        if (list == null)
//            ResultVOUtil.error(HttpStatus.NOT_FOUND, "查无此人");
//
//        return ResultVOUtil.success(
//                Map.of("userList", list)
//                , "模糊查询用户成功");
//    }
}
