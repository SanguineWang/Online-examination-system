package com.sanguinewang.oes.services;

import com.sanguinewang.oes.dataobject.Administrator;
import com.sanguinewang.oes.dataobject.Student;
import com.sanguinewang.oes.dataobject.Teacher;
import com.sanguinewang.oes.dataobject.User;
import com.sanguinewang.oes.repository.AdministratorRepository;
import com.sanguinewang.oes.repository.StudentRepository;
import com.sanguinewang.oes.repository.TeacherRepository;
import com.sanguinewang.oes.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public User findByUsername(User user) {
        return userRepository.findByName(user.getName()).orElse(null);
//        return userRepository.findByUsername(user.getUsername());
    }

    public User findUserById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
//        return userMapper.findUserById(userId);
    }

    public User findUserbyNumber(int userNum) {
        return userRepository.findByNumber(userNum).orElse(null);
    }

    public void addAdministrator(User u, Administrator t) {
//        直接用级联 不用再多一层save保存
//     userRepository.save(u);
//     t.setUser(u);
        administratorRepository.save(t);
    }

    public void addStudent(User user1, Student student) {
//        直接用级联 不用再多一层save保存
//     userRepository.save(u);
//     t.setUser(u);
        studentRepository.save(student);
    }

    public void addTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }
}
