package com.sanguinewang.oes.controller;

import com.sanguinewang.oes.VO.ResultVO;
import com.sanguinewang.oes.VO.TeacherExamDetailVO;
import com.sanguinewang.oes.annotation.UserLoginToken;
import com.sanguinewang.oes.component.RequestComponent;
import com.sanguinewang.oes.dataobject.*;
import com.sanguinewang.oes.service.TeacherService;
import com.sanguinewang.oes.service.UserService;
import com.sanguinewang.oes.util.ResultVOUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author SanguineWang
 * @Date 2020-07-06 9:17
 */
@RestController

@Slf4j
@RequestMapping("api/teacher/")
public class TeacherController {


    @Autowired
    private TeacherService teacherService;
    @Autowired
    private UserService userService;
    @Autowired
    private RequestComponent requestComponent;
    @ApiOperation("查询教师信息")
    @UserLoginToken
    @GetMapping("myInfo")
    public ResultVO getUser() {
        log.debug("{}",requestComponent.getUid());
        return ResultVOUtil.success(
                Map.of("myInfo", teacherService.findUserById(requestComponent.getUid()))
                , "获取教师个人信息成功");
    }

    @ApiOperation("修改个人信息")
    @PatchMapping("myInfo")
    @UserLoginToken
    public ResultVO updateUser(@RequestBody User user) {
        return ResultVOUtil.success(
                Map.of("myInfo", userService.updateUser(user))
                , "修改个人信息成功");
    }

    @ApiOperation("查看考试列表")
    @GetMapping("exam")
    @UserLoginToken
    public ResultVO getExamList() {

        return ResultVOUtil.success(
                Map.of("examList", teacherService.getExamList(requestComponent.getUid()))
                , "获取考试列表成功");
    }

    @ApiOperation("添加考试")
    @PostMapping("exam")
    @UserLoginToken
    public ResultVO addExam(@RequestBody Exam exam) {
        log.debug(exam.getName());
        teacherService.addExam(requestComponent.getUid(), exam);
        return ResultVOUtil.success(
                Map.of("examList", teacherService.getExamList(requestComponent.getUid()))
                , "添加考试成功");
    }

    @ApiOperation("删除考试")
    @DeleteMapping("exam/{eid}")
    @UserLoginToken
    public ResultVO removeExam(@PathVariable Integer eid) {
        teacherService.removeExam(eid);
        return ResultVOUtil.success(
                Map.of("examList", teacherService.getExamList(requestComponent.getUid()))
                , "删除考试成功");
    }

    @ApiOperation("修改考试基本信息")
    @PatchMapping("exam/{eid}")
    @UserLoginToken
    public ResultVO updateExam(@PathVariable Integer eid, @RequestBody Exam exam) {
        teacherService.updateExam(exam,eid);
        return ResultVOUtil.success(
                Map.of("examList", teacherService.getExamList(requestComponent.getUid()))
                , "修改考试信息");

    }

    @ApiOperation("查看考试详细信息")
    @GetMapping("exam/{eid}")
    @UserLoginToken
    public ResultVO getExam(@PathVariable Integer eid) {
        Exam exam =teacherService.getExam(eid);
//       封入VO
        TeacherExamDetailVO examDetailVO=new TeacherExamDetailVO();
        BeanUtils.copyProperties(exam,examDetailVO);
        return ResultVOUtil.success(
                Map.of("examDetail", examDetailVO)
                , "获取考试详细信息成功");

    }

    @ApiOperation("为考试导入学生")
    @PostMapping("exam/{eid}/students")
    @UserLoginToken
    public ResultVO addStudentsToExam(@PathVariable Integer eid, @RequestBody List<Student> studentList) {
        teacherService.addStudentsToExam(studentList, eid);

        Exam exam =teacherService.getExam(eid);
//       封入VO
        TeacherExamDetailVO examDetailVO=new TeacherExamDetailVO();
        BeanUtils.copyProperties(exam,examDetailVO);
        return ResultVOUtil.success(
                Map.of("examDetail", examDetailVO)
                , "获取考试详细信息成功");

    }

    @ApiOperation("为考试导入试卷")
    @PostMapping("exam/{eid}/paper")
    @UserLoginToken
    public ResultVO addPaperToExam(@PathVariable Integer eid, @RequestBody Exam e) {
        teacherService.addPaperToExam(eid,e);
        log.debug(e.toString());
        Exam exam =teacherService.getExam(eid);
//       封入VO
        TeacherExamDetailVO examDetailVO=new TeacherExamDetailVO();
        BeanUtils.copyProperties(exam,examDetailVO);
        return ResultVOUtil.success(
                Map.of("examDetail", examDetailVO)
                , "获取考试详细信息成功");
    }

    @ApiOperation("查看指定考试的指定学生的主观题试卷")
    @GetMapping("exam/{eid}/student/{sid}")
    @UserLoginToken
    public ResultVO getStudentExam(@PathVariable Integer eid, @PathVariable Integer sid) {
        log.debug("eid{}-sid-{}",eid,sid);
        return ResultVOUtil.success(
                Map.of("subjectiveList", teacherService.getStudentExamPaper(eid, sid))
                , "获取指定考试的指定学生的主观题试卷成功");
    }

    @ApiOperation("批卷,更新指定学生的主观题评分，批卷之后加和客观题成绩")
    @PatchMapping("exam/{eid}/student/{sid}")
    @UserLoginToken
    public ResultVO updateStudentsSubjective(@PathVariable Integer eid, @PathVariable Integer sid, @RequestBody List<Student_Subjective> student_subjectives) {
        teacherService.updateStudentSubjective(eid, sid, student_subjectives);
        return ResultVOUtil.success(
                Map.of("paper", teacherService.getStudentExamPaper(eid, sid))
                , "获取指定考试的指定学生的主观题试卷成功");
    }

    @ApiOperation("成绩统计,返回考试的所有学生-考试信息")
    @GetMapping("exam/{eid}/grade")
    @UserLoginToken
    public ResultVO getStudentExamList(@PathVariable Integer eid) {

        return ResultVOUtil.success(
                Map.of("studentExamList", teacherService.getStudentExamList(eid))
                , "获取信息成功");
    }
}
