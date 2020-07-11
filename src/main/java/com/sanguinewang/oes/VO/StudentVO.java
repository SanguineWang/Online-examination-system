package com.sanguinewang.oes.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanguinewang.oes.dataobject.Student_Exam;
import com.sanguinewang.oes.dataobject.User;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Description: oes
 * Created by Rice on 2020/7/10 23:32
 * 用于StudentController层的VO
 */
@Data
public class StudentVO {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer id;
    private User user;
//    private List<Student_Exam> studentExams;
}
