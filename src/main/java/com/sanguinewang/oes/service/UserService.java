package com.sanguinewang.oes.service;

import com.sanguinewang.oes.dataobject.Administrator;
import com.sanguinewang.oes.dataobject.Student;
import com.sanguinewang.oes.dataobject.Teacher;
import com.sanguinewang.oes.dataobject.User;
import com.sanguinewang.oes.repository.AdministratorRepository;
import com.sanguinewang.oes.repository.StudentRepository;
import com.sanguinewang.oes.repository.TeacherRepository;
import com.sanguinewang.oes.repository.UserRepository;
import com.sanguinewang.oes.util.ResultVOUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

/**
 * Description: oes
 * Created by Rice on 2020/7/6 10:13
 */
@Service("UserService")
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final AdministratorRepository administratorRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public UserService(UserRepository userRepository,
                       AdministratorRepository administratorRepository,
                       StudentRepository studentRepository,
                       TeacherRepository teacherRepository) {
        this.userRepository = userRepository;
        this.administratorRepository = administratorRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    /**
     * 查询个人信息
     *
     * @param userId
     * @return
     */
    public User findUserById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "当前用户不存在"));
    }


    /**
     * 修改个人信息
     *
     * @param u
     * @return
     */
    public User updateUser(User u) {
        User user = userRepository.findById(u.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "当前用户不存在"));
        user.setName(u.getName());
        return userRepository.saveAndFlush(user);
    }

    public User findByUsername(User user) {
        return userRepository.findByName(user.getName()).orElse(null);
//        return userRepository.findByUsername(user.getUsername());
    }



    public User findUserbyNumber(int userNum) {
        return userRepository.findByNumber(userNum).orElse(null);
    }

    public void addAdministrator(Administrator t) {

        administratorRepository.save(t);
    }

    public void addStudent(User user1, Student student) {
//        直接用级联 不用再多一层save保存
//     userRepository.save(u);
//     t.setUser(u);
        studentRepository.save(student);
    }

    public void addTeacher(User user1, Teacher teacher) {
        teacherRepository.save(teacher);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
