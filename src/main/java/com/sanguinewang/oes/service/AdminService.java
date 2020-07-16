package com.sanguinewang.oes.service;

import com.sanguinewang.oes.dataobject.Administrator;
import com.sanguinewang.oes.dataobject.Exam;
import com.sanguinewang.oes.dataobject.Student_Exam;
import com.sanguinewang.oes.dataobject.User;
import com.sanguinewang.oes.enums.RoleEnums;
import com.sanguinewang.oes.repository.*;
import javassist.expr.NewArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author SanguineWang
 * @Date 2020-07-07 8:16
 */
@Service("AdminService")
@Transactional
@Slf4j
public class AdminService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private AdministratorRepository administratorRepository;
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    Student_ExamRepository student_examRepository;

    public List<Exam> getExam() {
        LocalDateTime dateTime = LocalDateTime.now();
        List<Exam> exams = examRepository.list();
        List<Exam> exams1 = null;
        for (Exam exam : exams) {
            if (exam.getStartTime().isBefore(dateTime) && exam.getEndTime().isAfter(dateTime)) {
                exams1.add(exam);
            }

        }
        if (exams1 != null)
            return exams1;
        else
            return null;
    }


    public List<Student_Exam> getStudent(Integer eid) {
        return student_examRepository.findStudent_ExamByExamId(eid);
    }

    public User getUser(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByNum(int number) {
        return userRepository.findByNumber(number).orElse(null);
    }

    //返回学生列表
    public List<User> getStudentList() {
        List<User> students = new ArrayList<>();

        for (User s:userRepository.list()
             ) {log.debug("{lalaalallala}", s.getRole());
            if(s.getRole()==RoleEnums.STUDENT)
            {students.add(s);}
        }
        return students;

    }

    //返回教师列表
    public List<User> getTeacherList() {
        List<User> teachers =new ArrayList<>();
        for (User s:userRepository.list()
        ) {
            if(s.getRole()==RoleEnums.TEACHER)
                teachers.add(s);
        }
        return teachers;
    }

    public User modifyUser(int uid, Integer number, String name, RoleEnums role) {
        //修改用户账号
        //修改用户角色code
        //修改用户角色msg
        //修改用户姓名
        if (number != null)
            getUser(uid).setNumber(number);

        if (name != null)
            getUser(uid).setName(name);

        if (role != null)
            getUser(uid).setRole(role);

        return getUser(uid);
    }


    //删除用户
    public void deleteUser(Integer uid) {
        User user = userRepository.findById(uid).get();
        RoleEnums roleEnums = user.getRole();
        if (roleEnums == RoleEnums.STUDENT) {
            studentRepository.deleteById(uid);
            log.debug("删除学生");
        }
        if (roleEnums == RoleEnums.TEACHER) {
            teacherRepository.deleteById(uid);
            log.debug("删除老师");
        }
        log.debug("没删除老师");
    }

    //按角色查询用户(是枚举类）
    public List<User> selectUserByCode(RoleEnums role) {
        return userRepository.findByRole(role);
    }

    //按姓名模糊查询用户
    public List<User> selectUserByNum(String name) {
       return userRepository.findByNumberLike(name);

    }
}
