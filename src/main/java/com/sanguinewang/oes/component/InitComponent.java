package com.sanguinewang.oes.component;

import com.sanguinewang.oes.dataobject.Administrator;
import com.sanguinewang.oes.dataobject.Student;
import com.sanguinewang.oes.dataobject.Teacher;
import com.sanguinewang.oes.dataobject.User;
import com.sanguinewang.oes.enums.RoleEnums;
import com.sanguinewang.oes.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InitComponent implements InitializingBean {
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserService userService;

    @Override
    public void afterPropertiesSet() throws Exception {
        int num = 1001;
        User user = userService.findUserbyNumber(num);
        if (user == null) {
            User newUser = new User();
            newUser.setName("Admin");
            newUser.setNumber(num);
            newUser.setRole(RoleEnums.ADMINISTRATOR);
            /** String和CharSequence的关系
             *    String 实现了CharSequence接口
             * public final class String
             *     implements java.io.Serializable, Comparable<String>, CharSequence{ }
             */
            newUser.setPassword(encoder.encode(String.valueOf(num)));

            Administrator admin = new Administrator();
            admin.setUser(newUser);
            userService.addAdministrator( admin);
        }

        int stu_num = 2007;
        User stu_user = userService.findUserbyNumber(stu_num);
        if (stu_user == null) {
            User newStuUser = new User();
            newStuUser.setName("Default_student");
            newStuUser.setNumber(stu_num);
            newStuUser.setRole(RoleEnums.STUDENT);
            /** String和CharSequence的关系
             *    String 实现了CharSequence接口
             * public final class String
             *     implements java.io.Serializable, Comparable<String>, CharSequence{ }
             */
            newStuUser.setPassword(encoder.encode(String.valueOf(stu_num)));
            Student student = new Student();
            student.setUser(newStuUser);
            userService.addStudent(newStuUser, student);
        }

        int teacher_num = 3001;
        User teacher_user = userService.findUserbyNumber(teacher_num);
        if (teacher_user == null) {
            User newStuUser = new User();
            newStuUser.setName("Default_teacher");
            newStuUser.setNumber(teacher_num);
            newStuUser.setRole(RoleEnums.TEACHER);
            newStuUser.setPassword(encoder.encode(String.valueOf(teacher_num)));
            Teacher teacher = new Teacher();
            teacher.setUser(newStuUser);
            userService.addTeacher(newStuUser, teacher);
        }
    }
}
