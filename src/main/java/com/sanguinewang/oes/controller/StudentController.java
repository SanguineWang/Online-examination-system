package com.sanguinewang.oes.controller;

import com.sanguinewang.oes.VO.ResultVO;
import com.sanguinewang.oes.dataobject.Exam;
import com.sanguinewang.oes.dataobject.Student_Exam;
import com.sanguinewang.oes.dataobject.User;
import com.sanguinewang.oes.util.ResultVOUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Description: oes
 * Created by Rice on 2020/7/6 9:13
 */
@RestController
@Slf4j
@RequestMapping("/api/students/")
public class StudentController {

    @ApiOperation("查询个人信息")
    @GetMapping("myInfo")
    public ResultVO getUser() {

        return ResultVOUtil.success(
                Map.of("myInfo", new User())
                , "获取个人信息成功");
    }

    @ApiOperation("修改个人信息")
    @PatchMapping("myInfo")
    public ResultVO updateUser() {
        return ResultVOUtil.success(
                Map.of("myInfo", new User())
                , "修改个人信息成功");

    }

    @ApiOperation("获取考试列表")
    @GetMapping("exam")
    public ResultVO getExamList() {
        return ResultVOUtil.success(
                Map.of("examList", List.of( new Exam()))
                , "查询成功"
        );
    }

    @ApiOperation("获取指定考试试卷(进入考试)")
    @GetMapping("exam/{eid}")
    public ResultVO getExam(@PathVariable Integer eid) {

        return ResultVOUtil.success(
                Map.of("examList", List.of( new Exam()))
                , "查询成功"
        );
    }
    @ApiOperation("提交考试（上传答题卡）")
    @PostMapping("exam/{eid}")
    public ResultVO addStudentPaper(@PathVariable Integer eid,@RequestBody Exam exam) {

        return ResultVOUtil.success(
                Map.of("examList", List.of( new Exam()))
                , "上传成功"
        );
    }

    @ApiOperation("查看我的成绩,返回各次考试的成绩")
    @GetMapping("myGrade")
    public ResultVO getGradeList() {

        return ResultVOUtil.success(
                Map.of("examList", List.of( new Student_Exam()))
                , "查询成功"
        );
    }
}
