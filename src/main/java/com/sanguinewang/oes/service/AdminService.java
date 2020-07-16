package com.sanguinewang.oes.service;

import com.sanguinewang.oes.dataobject.*;
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



    //返回学生列表
    public  List<Student> getStudentList() {
        return studentRepository.findAll();
    }

    //返回教师列表
    public List<Teacher> getTeacherList() {
        return teacherRepository.findAll();
    }

//    public User modifyUser(int uid, Integer number, String name, RoleEnums role) {
//        //修改用户账号
//        //修改用户角色code
//        //修改用户角色msg
//        //修改用户姓名
//        if (number != null)
//            getUser(uid).setNumber(number);
//
//        if (name != null)
//            getUser(uid).setName(name);
//
//        if (role != null)
//            getUser(uid).setRole(role);
//
//        return getUser(uid);
//    }


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


}
