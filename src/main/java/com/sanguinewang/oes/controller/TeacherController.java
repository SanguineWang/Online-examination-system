package com.sanguinewang.oes.controller;

import com.sanguinewang.oes.dataobject.Exam;
import com.sanguinewang.oes.dataobject.Student_Exam;
import com.sanguinewang.oes.dataobject.User;
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


    @ApiOperation("查询个人信息")
    @GetMapping("myInfo")
    public Map getUser() {

        return Map.of("myInfo", new User());
    }

    @ApiOperation("修改个人信息")
    @PatchMapping("myInfo")
    public Map updateUser() {

        return Map.of("myInfo", new User());
    }

    @ApiOperation("查看考试列表")
    @GetMapping("exam")
    public Map getExamList() {
        List<Exam> examList = null;
        return Map.of("exam_list", List.of(new Exam()));
    }

    @ApiOperation("添加考试")
    @PostMapping("exam")
    public Map addExam(@RequestBody Exam exam) {
        return Map.of("exam_list", List.of(new Exam()));
    }

    @ApiOperation("删除考试")
    @DeleteMapping("exam/{eid}")
    public Map removeExam(@PathVariable Integer eid) {

        return Map.of("exam_list", List.of(new Exam()));
    }

    @ApiOperation("修改考试信息")
    @PatchMapping("exam/{eid}")
    public Map updateExam(@PathVariable Integer eid, @RequestBody Exam exam) {

        return Map.of("exam", new Exam());
    }

    @ApiOperation("查看考试详细信息")
    @GetMapping("exam/{eid}")
    public Map getExam(@PathVariable Integer eid) {

        return Map.of("exam", new Exam());
    }


    @ApiOperation("查看指定考试的学生主观题试卷")
    @GetMapping("exam/{eid}/student/")
    public Map getStudentExam(@PathVariable Integer eid) {

        return Map.of("exam", new Exam());
    }

}
