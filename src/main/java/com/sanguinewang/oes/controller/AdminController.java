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
public class AdminController {

    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AdminService adminService;
    @Autowired
    StudentRepository studentRepository;

    @ApiOperation("进入在线考试后台,查看在线考试列表")
    @GetMapping("onlineExamList")
    @UserLoginToken
    public ResultVO getBackgroundList(){
        if(adminService.getExam()==null)
            ResultVOUtil.error(HttpStatus.NOT_FOUND, "无进行中考试");
            return ResultVOUtil.success(
                    Map.of("examList", adminService.getExam())
                    , "进入后台成功");
    }

    @ApiOperation("进入在线考试后台,查看学生交卷情况")
    @GetMapping("onlineExamId/{examId}")
    @UserLoginToken
    public ResultVO getOnlineStudent(@PathVariable Integer examId){
        //adminService.getStudent(examId);
        if(adminService.getStudent(examId)==null)
            ResultVOUtil.error(HttpStatus.NOT_FOUND,"无学生进行考试这场考试");

        return ResultVOUtil.success(
                    Map.of("studentList",adminService.getStudent(examId) )
                    , "查看学生列表成功");
    }

    @ApiOperation("获取学生信息列表")
    @GetMapping("students")
    @UserLoginToken
    public ResultVO getStudentList(){
        if(adminService.getStudentList()==null)
            ResultVOUtil.error(HttpStatus.NOT_FOUND, "无学生");

        return ResultVOUtil.success(
                Map.of("studentList", adminService.getStudentList())
                , "获取学生列表成功");
    }

    @ApiOperation("获取教师信息列表")
    @GetMapping("teachers")
    @UserLoginToken
    public ResultVO getTeacherList(){
        if(adminService.getTeacherList()==null)
            ResultVOUtil.error(HttpStatus.NOT_FOUND, "无教师");
        return ResultVOUtil.success(
                Map.of("studentList", adminService.getTeacherList())
                , "获取教师列表成功");
    }

    @ApiOperation("修改用户信息")
    @PatchMapping("user/{uid}")
    @UserLoginToken
    public ResultVO updateUser(@RequestBody User user,@PathVariable Integer uid){

        return ResultVOUtil.success(
                Map.of("userInfo", adminService.modifyUser(uid, user.getNumber(),user.getName(),user.getRole()))
                , "修改用户信息成功");
    }
    @ApiOperation("删除学生")
    @DeleteMapping("students/{uid}")
    @UserLoginToken
    public ResultVO deleteStudent(@PathVariable int uid){
         adminService.deleteUser(uid);
        return ResultVOUtil.success(
                Map.of("myInfo",adminService.getStudentList())
                , "删除学生信息成功");
    }

    @ApiOperation("删除教师")
    @DeleteMapping("teachers/{uid}")
    @UserLoginToken
    public ResultVO deleteTeacher(@PathVariable int uid){
        adminService.deleteUser(uid);
        return ResultVOUtil.success(
                Map.of("myInfo",adminService.getTeacherList())
                , "删除教师信息成功");
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
        userService.addAdministrator(administrator);
        return ResultVOUtil.success(
                Map.of("myInfo", administrator)
                , "添加用户成功");
    }
    /*
    * 模糊查询
    * */
    @ApiOperation("按姓名查询用户")
    @GetMapping("userName")
    @UserLoginToken
    @ResponseBody
    public ResultVO selectUserByNum(HttpServletRequest req){

        String likeName = req.getParameter("name");
        List<User> list = null;
        //这里需要用到模糊查询的通配符
        list = adminService.selectUserByNum("%"+likeName+"%");
        if(list ==null)
            ResultVOUtil.error(HttpStatus.NOT_FOUND,"查无此人");

        return ResultVOUtil.success(
                Map.of("userList", list)
                , "模糊查询用户成功");
    }

}
