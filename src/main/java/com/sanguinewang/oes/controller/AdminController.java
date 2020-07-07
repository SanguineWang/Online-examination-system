package com.sanguinewang.oes.controller;

import com.sanguinewang.oes.VO.ResultVO;
import com.sanguinewang.oes.dataobject.Exam;
import com.sanguinewang.oes.dataobject.User;
import com.sanguinewang.oes.util.ResultVOUtil;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("进入在线考试后台,查看学生答题情况")
    @GetMapping("background")
    public ResultVO getBackgroundList(){
        return ResultVOUtil.success(
                Map.of("examList", List.of(new Exam()))
                , "进入后台成功");
    }
    @ApiOperation("获取用户信息列表")
    @GetMapping("user")
    public ResultVO getUserList(){
        return ResultVOUtil.success(
                Map.of("userList", List.of(new User()))
                , "获取用户列表成功");
    }
    @ApiOperation("修改用户信息")
    @PatchMapping("user/{uid}")
    public ResultVO updateUser(@RequestBody User user , @PathVariable Integer uid){
        return ResultVOUtil.success(
                Map.of("userInfo", new User())
                , "修改用户信息成功");
    }
    @ApiOperation("删除用户信息")
    @DeleteMapping("user/{uid}")
    public ResultVO deleteUser(@PathVariable Integer uid){
        return ResultVOUtil.success(
                Map.of("myInfo", List.of(new User()))
                , "删除用户信息成功");
    }
    @ApiOperation("添加用户信息")
    @PostMapping("user")
    public ResultVO addUser(@RequestBody User user){
        return ResultVOUtil.success(
                Map.of("myInfo", List.of(new User()))
                , "添加用户信息成功");
    }
}
