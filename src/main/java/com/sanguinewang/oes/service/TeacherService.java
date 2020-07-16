package com.sanguinewang.oes.service;

import com.sanguinewang.oes.VO.ResultVO;
import com.sanguinewang.oes.dataobject.*;
import com.sanguinewang.oes.enums.RoleEnums;
import com.sanguinewang.oes.repository.*;
import com.sanguinewang.oes.util.ResultVOUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author SanguineWang
 * @Date 2020-07-07 8:03
 */
@Service("TeacherService")
@Transactional
@Slf4j
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private Student_ExamRepository student_examRepository;
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChoiceRepository choiceRepository;
    @Autowired
    private JudgmentRepositry judgmentRepositry;
    @Autowired
    private SubjectiveRepository subjectiveRepository;
    @Autowired
    private Student_ChoiceRepository student_choiceRepository;
    @Autowired
    private Student_JudgmentRepository student_judgmentRepository;
    @Autowired
    private Student_SubjectiveRepository student_subjectiveRepository;
    @Autowired
    private PasswordEncoder encoder;

    /**
     * 查询个人信息
     */
    public Teacher findUserById(Integer tid) {
        return teacherRepository.findById(tid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "当前用户不存在"));
    }


    /**
     * 修改个人用户信息,不包括考试列表
     *
     * @param u
     * @return
     */
    public User updateUser(User u) {
        User user = userRepository.findById(u.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "当前用户不存在"));
        user.setName(u.getName());
        return userRepository.saveAndFlush(user);
    }
    /**
     * 更新密码
     *
     * @param tid      teacherId

     */
    public void updatePassword(Integer tid, String oldPassword,String newPassword) {
        if (oldPassword.equals(newPassword)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"新密码不能与旧密码一致");
        }
        Teacher teacher = teacherRepository.findById(tid).get();
        if (encoder.matches(oldPassword,teacher.getUser().getPassword())){

            teacher.getUser().setPassword(encoder.encode(newPassword));
            teacherRepository.save(teacher);
        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"密码错误");
        }
    }
    /**
     * 查看考试列表
     *
     * @param tid User Id
     * @return ExamList
     */
    public List<Exam> getExamList(Integer tid) {
        Teacher teacher = teacherRepository.findById(tid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "当前用户不存在"));

        return teacher.getExamList() == null ? List.of(new Exam()) : teacher.getExamList();
    }


    /**
     * 添加考试，只添加基础信息
     *
     * @param tid teacher Id
     * @param e   Exam
     * @return Exam
     */
    public Exam addExam(Integer tid, Exam e) {
        Teacher teacher = teacherRepository.findById(tid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "当前用户不存在"));
        Exam exam = new Exam();
        exam.setTeacher(teacher);
        exam.setName(e.getName());
        exam.setStartTime(e.getStartTime());
        exam.setEndTime(e.getEndTime());
        return examRepository.saveAndFlush(exam);
    }

    /**
     * 删除考试
     *
     * @param eid Exam Id
     */
    public void removeExam(Integer eid) {
        examRepository.deleteById(eid);
    }

    /**
     * 修改考试信息,考试名和时间
     *
     * @param e Exam
     * @return new exam
     */
    public Exam updateExam(Exam e, Integer eid) {
        Exam exam = examRepository.findById(eid).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "考试不存在"));
        exam.setName(e.getName());
//        设置时间
        exam.setStartTime(e.getStartTime());
        exam.setEndTime(e.getEndTime());
        return examRepository.saveAndFlush(exam);
    }

    /**
     * 向考试导入学生，多次导入先删除原记录
     *
     * @param studentList
     * @param eid
     * @return
     */
    public void addStudentsToExam(List<Student> studentList, Integer eid) {
        Exam exam = examRepository.findById(eid).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "考试不存在"));
//        清空旧的学生-考试记录
        student_examRepository.removeByExamId(eid);
        studentList.forEach(s -> {
            Student student = addStudent(s);
            Student_Exam studentExam = new Student_Exam();
            studentExam.setExam(exam);
            studentExam.setStudent(student);
            student_examRepository.save(studentExam);
        });

    }

    /**
     * 向考试导入试卷
     *
     * @return
     */
    public void addPaperToExam(Integer eid, Exam e) {
        Exam exam = examRepository.findById(eid).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "考试不存在"));
//        清空之前的
        try{
            choiceRepository.deleteByExamId(eid);
            judgmentRepositry.deleteByExamId(eid);
            subjectiveRepository.deleteByExamId(eid);
        }catch (DataIntegrityViolationException e1){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"更新失败，考试已开始");
        }

//        导入新的
        for (Choice choice : e.getChoiceList()) {
            choice.setExam(exam);
            choiceRepository.save(choice);
        }
        for (Judgment judgment : e.getJudgmentList()) {
            judgment.setExam(exam);
            judgmentRepositry.save(judgment);
        }
        for (Subjective subjective : e.getSubjectiveList()) {
            subjective.setExam(exam);
            subjectiveRepository.save(subjective);
        }

        examRepository.save(exam);
    }

    /**
     * 数据库中没有该学生，持久化学生同时级联持久化user,如果存在，则返回
     */
    public Student addStudent(Student s) {
        Student student = studentRepository.findbyNumber(s.getUser().getNumber());
        if (student == null) {
            User user = new User();
            user.setNumber(s.getUser().getNumber());
            user.setName(s.getUser().getName());
            //固有信息
            user.setRole(RoleEnums.STUDENT);
            user.setPassword(encoder.encode("1234"));
            student = new Student();
            student.setUser(user);
            student = studentRepository.saveAndFlush(student);
//            studentRepository.refresh(student);
            log.debug(student.toString());
            return student;
        } else {
            return student;
        }
    }

    public Exam getExam(Integer eid) {
        return examRepository.findExamByExamId(eid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "当前考试不存在"));
    }

//    /**
//     * 查看考试学生列表
//     *
//     * @param eid Exam Id
//     * @return exam
//     */
//    public List<Student_Exam> getExamStudents(Integer eid) {
//
//        return ;
//    }
//    /**
//     * 查看考试选择题
//     *
//     * @param eid Exam Id
//     * @return exam
//     */
//    public List<Student_Exam> getExamStudents(Integer eid) {
//
//        return ;
//    }
//    /**
//     * 查看考试判断题
//     *
//     * @param eid Exam Id
//     * @return exam
//     */
//    public List<Student_Exam> getExamStudents(Integer eid) {
//
//        return ;
//    }
//    /**
//     * 查看考试主观题
//     *
//     * @param eid Exam Id
//     * @return exam
//     */
//    public List<Student_Exam> getExamStudents(Integer eid) {
//
//        return ;
//    }

    /**
     * 查看指定考试的指定学生的主观题试卷
     *
     * @param eid 考试id
     * @param sid 学生id
     * @return 学生主观题答题卡
     */
    public List<Student_Subjective> getStudentExamPaper(Integer eid, Integer sid) {
        Student student = studentRepository.findById(sid).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "学生不存在"));
        List<Student_Subjective> student_subjectiveList = student.getStudent_subjectiveList();
        if (student_subjectiveList != null) {
            return student_subjectiveList
                    .stream()
                    .filter(student_subjective -> student_subjective.getSubjective().getExam().getId().equals(eid))
                    .collect(Collectors.toList());
        } else {
            log.debug("学生主观题答题卡为空");
            return List.of(new Student_Subjective());
        }

    }

    /**
     * 批卷,更新指定学生的所有主观题评分，批卷之后加和客观题成绩
     *
     * @param eid                   考试id
     * @param sid                   教师id
     * @param studentSubjectiveList 主观题答题卡列表
     * @return
     */
    public void updateStudentSubjective(Integer eid, Integer sid, List<Student_Subjective> studentSubjectiveList) {

        float subjectiveGrade = 0.0f;
        for (Student_Subjective ss : studentSubjectiveList) {
            Student_Subjective student_subjective = student_subjectiveRepository.findById(ss.getId()).get();
            student_subjective.setScore(ss.getScore());
            //        加和主观题分数
            subjectiveGrade += ss.getScore();
        }
        ;
        Student_Exam student_exam = student_examRepository.findExamByStudentUidAndExamId(sid, eid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "student_exam不存在"));
        student_exam.setSubjectiveGrade(subjectiveGrade);
        student_examRepository.save(student_exam);
    }

    /**
     * 成绩统计,返回考试的所有学生-考试信息
     *
     * @param eid exam id
     * @return
     */
    public List<Student_Exam> getStudentExamList(Integer eid) {
        Exam exam = examRepository.findById(eid).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "考试不存在"));
        List<Student_Exam> studentExams=exam.getStudentExams();
        if(studentExams==null){
            return List.of(new Student_Exam());
        }else {
            return studentExams = studentExams.stream()
                    .filter(Student_Exam::isSubmit)
                    .collect(Collectors.toList());
        }
    }

}
