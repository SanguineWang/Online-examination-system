package com.sanguinewang.oes.services;

import com.sanguinewang.oes.repository.AdministratorRepository;
import com.sanguinewang.oes.repository.StudentRepository;
import com.sanguinewang.oes.repository.TeacherRepository;
import com.sanguinewang.oes.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description
 * @Author SanguineWang
 * @Date 2020-07-07 8:03
 */
@Service("TeacherService")
@Transactional
@Slf4j
public class TeacherService {
    private final UserRepository userRepository;
    private final AdministratorRepository administratorRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public TeacherService(UserRepository userRepository,
                       AdministratorRepository administratorRepository,
                       StudentRepository studentRepository,
                       TeacherRepository teacherRepository) {
        this.userRepository = userRepository;
        this.administratorRepository = administratorRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }


}
