package com.sanguinewang.oes.controller;

import com.sanguinewang.oes.VO.ExamDetailVO;
import com.sanguinewang.oes.VO.ExamVO;
import com.sanguinewang.oes.VO.ResultVO;
import com.sanguinewang.oes.VO.StudentVO;
import com.sanguinewang.oes.annotation.UserLoginToken;
import com.sanguinewang.oes.component.RequestComponent;
import com.sanguinewang.oes.dataobject.Choice;
import com.sanguinewang.oes.dataobject.Exam;
import com.sanguinewang.oes.dataobject.Student;
import com.sanguinewang.oes.dataobject.Student_Exam;
import com.sanguinewang.oes.enums.RoleEnums;
import com.sanguinewang.oes.service.StudentService;
import com.sanguinewang.oes.util.ResultVOUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description: oes
 * Created by Rice on 2020/7/6 9:13
 */
@RestController
@Slf4j
@RequestMapping("/api/students/")
public class StudentController {

    @Autowired
    private RequestComponent requestComponent;
    @Autowired
    private StudentService studentService;

    @ApiOperation("查询个人信息")
    @UserLoginToken
    @GetMapping("myInfo")
    public ResultVO getUser() {
        int uid = requestComponent.getUid();
        Student studentFromDB = studentService.findStudentByUid(uid);
        //使用studentVO进行封装，防止暴露出考试内容
        StudentVO studentVO = new StudentVO();
        BeanUtils.copyProperties(studentFromDB, studentVO);
        return ResultVOUtil.success(
                Map.of("myInfo", studentVO)
                , "查询个人信息成功");
    }


    @ApiOperation("修改个人信息")
    @UserLoginToken
    @Transactional
    @PatchMapping("myInfo")
    public ResultVO updateUser(@RequestBody Student student) {
        int uid = requestComponent.getUid();
        Student studentFromDB = studentService.findStudentByUid(uid);
        studentFromDB.getUser().setName(student.getUser().getName());
        StudentVO studentVO = new StudentVO();
        BeanUtils.copyProperties(studentFromDB, studentVO);
        return ResultVOUtil.success(
                Map.of("myInfo", studentVO)
                , "修改个人信息成功");
    }

    @ApiOperation("获取考试列表")
    @UserLoginToken
    @GetMapping("exam")
    public ResultVO getExamList() {
        int uid = requestComponent.getUid();
        Student studentFromDB = studentService.findStudentByUid(uid);
        List<Student_Exam> studentExams = studentFromDB.getStudentExams();
        List<ExamVO> list = studentExams.stream().map(studentExam -> new ExamVO(
                studentExam.getExam().getId(),//exam id
                studentExam.getExam().getName(),//考试名称
                studentExam.getExam().getStartTime(),//开始时间
                studentExam.getExam().getEndTime(),//结束时间
                studentExam.isSubmit() ,     //是否提交
                studentExam.getStudent().getUser().getName(),//考生姓名
                studentExam.getExam().getTeacher().getUser().getName(),//教师姓名
                studentExam.getExam().getTeacher().getUser().getNumber(),//教师number
                studentExam.getStudent().getUser().getNumber()//考生number
        )).collect(Collectors.toList());
        return ResultVOUtil.success(
                Map.of("length", list.size(), "examList", list)
                , "查询成功"
        );
    }

    @ApiOperation("获取指定考试试卷(进入考试)")
    @UserLoginToken
    @GetMapping("exam/{eid}")
    public ResultVO getExam(@PathVariable Integer eid) {
        ExamDetailVO examDetailVO = new ExamDetailVO();
        int myUid = requestComponent.getUid();
        //判断学生是否能参加该考试
        Boolean joinability = studentService.VerifyJoinability(eid, myUid);
        if (joinability) {
            Exam examFromDB = studentService.getExam(eid);
            BeanUtils.copyProperties(examFromDB, examDetailVO);
        }
        return ResultVOUtil.success(
                Map.of("exam", examDetailVO)
                , "查询成功"
        );
    }

    @ApiOperation("提交考试（上传答题卡）")
    @UserLoginToken
    @Transactional
    @PostMapping("exam/{eid}")
    public ResultVO addStudentPaper(@PathVariable Integer eid, @RequestBody Exam exam) {
        //用户id
        int myUid = requestComponent.getUid();
        Boolean aBoolean = studentService.VerifyJoinability(eid, myUid);
        //判断当前考试能否参加
        if (aBoolean) {
            //修改当前考试状态为已经提交，防止重复提交
            studentService.submitExam(eid, myUid);
            Student studentFromDB = studentService.findStudentByUid(myUid);
            exam.getChoiceList()
                    .forEach(choice -> studentService.saveChoice(choice.getId(), studentFromDB, choice.getAnswer()));
            exam.getJudgmentList()
                    .forEach(judgment -> studentService.saveJudgment(judgment.getId(), studentFromDB, judgment.getAnswer()));
            exam.getSubjectiveList()
                    .forEach(subjective -> studentService.saveSubject(subjective.getId(), studentFromDB, subjective.getAnswer()));

            studentService.calculateObject(eid, myUid);
            return ResultVOUtil.success(
                    Map.of("examList", List.of(new Exam()))
                    , "上传成功");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "当前考试不可用");
        }
    }

    @ApiOperation("查看我的成绩,返回各次考试的成绩")
    @UserLoginToken
    @GetMapping("myGrade")
    public ResultVO getGradeList() {
        List<Student_Exam> examList = studentService.listGrade(requestComponent.getUid());
        List<ExamVO> examVOList = new ArrayList<>();
        examList.forEach(studentExam -> {
            ExamVO examVO = new ExamVO(
                    studentExam.getId(),//考试id
                    studentExam.getExam().getName(),//考试名称
                    studentExam.getExam().getStartTime(),//开始时间
                    studentExam.getExam().getEndTime(),//结束时间
                    studentExam.isSubmit() ,     //是否提交
                    studentExam.getStudent().getUser().getName(),//考生姓名
                    studentExam.getExam().getTeacher().getUser().getName(),//教师姓名
                    studentExam.getExam().getTeacher().getUser().getNumber(),//教师number
                    studentExam.getStudent().getUser().getNumber()//考生number
            );
            examVO.setObjectiveGrade(studentExam.getObjectiveGrade());
            examVO.setSubjectiveGrade(studentExam.getSubjectiveGrade());
            examVOList.add(examVO);
        });
        return ResultVOUtil.success(
                Map.of("examList", examVOList, "length", examVOList.size())
                , "查询成功"
        );
    }
}
