package com.sanguinewang.oes.controller;

import com.sanguinewang.oes.VO.ResultVO;
import com.sanguinewang.oes.component.RequestComponent;
import com.sanguinewang.oes.dataobject.Exam;
import com.sanguinewang.oes.dataobject.Student_Exam;
import com.sanguinewang.oes.dataobject.Subjective;
import com.sanguinewang.oes.dataobject.User;
import com.sanguinewang.oes.util.ResultVOUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author SanguineWang
 * @Date 2020-07-06 9:17
 */
@RestController
@RequestMapping("api/teacher/")
public class TeacherController {

    @Autowired
    private RequestComponent requestComponent;


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

    @ApiOperation("查看考试列表")
    @GetMapping("exam")
    public ResultVO getExamList() {

        return ResultVOUtil.success(
                Map.of("examList", List.of(new Exam()))
                , "获取考试列表成功");
    }

    @ApiOperation("添加考试,")
    @PostMapping("exam")
    public ResultVO addExam(@RequestBody Exam exam) {

        return ResultVOUtil.success(
                Map.of("examList", List.of(new Exam()))
                , "添加考试成功");
    }

    @ApiOperation("删除考试")
    @DeleteMapping("exam/{eid}")
    public ResultVO removeExam(@PathVariable Integer eid) {

        return ResultVOUtil.success(
                Map.of("examList", List.of(new Exam()))
                , "删除考试成功");
    }

    @ApiOperation("修改考试信息")
    @PatchMapping("exam/{eid}")
    public ResultVO updateExam(@PathVariable Integer eid, @RequestBody Exam exam) {
        return ResultVOUtil.success(
                Map.of("exam", List.of(new Exam()))
                , "修改考试信息");

    }

    @ApiOperation("查看考试详细信息")
    @GetMapping("exam/{eid}")
    public ResultVO getExam(@PathVariable Integer eid) {

        return ResultVOUtil.success(
                Map.of("exam", new Exam())
                , "获取考试详细信息成功");

    }


    @ApiOperation("查看指定考试的指定学生的主观题试卷")
    @GetMapping("exam/{eid}/student/{tid}")
    public ResultVO getStudentExam(@PathVariable Integer eid, @PathVariable Integer tid) {

        return ResultVOUtil.success(
                Map.of("subjectiveList", List.of(new Subjective()))
                , "获取考试详细信息成功");
    }

    @ApiOperation("批卷,更新指定学生的主观题评分，批卷之后加和客观题成绩")
    @PatchMapping("exam/{eid}/student/{tid}")
    public ResultVO updateStudentsSubjective(@PathVariable Integer eid, @PathVariable Integer tid,@RequestBody List<Subjective> subjectiveList) {

      return ResultVOUtil.success(
                Map.of("student_exam", List.of(new Student_Exam()))
                , "获取信息成功");
    }

    @ApiOperation("成绩统计,返回考试的所有学生-考试信息")
    @GetMapping("exam/{eid}/grade")
    public ResultVO getStudentExamList(@PathVariable Integer eid) {
        return ResultVOUtil.success(
                Map.of("student_exam", List.of(new Student_Exam()))
                , "获取信息成功");
    }
}
