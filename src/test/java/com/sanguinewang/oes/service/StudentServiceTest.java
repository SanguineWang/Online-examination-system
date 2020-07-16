package com.sanguinewang.oes.service;

import com.sanguinewang.oes.dataobject.Student;
import com.sanguinewang.oes.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class StudentServiceTest {


    @Autowired
    private StudentService studentService;

//    @Test
//    void findStudentInfoByUid() {
//        Student studentInfoByUid = studentService.findStudentByUid(2);
//        log.info("{}", studentInfoByUid.getUser().getName());
//    }
}