package com.sanguinewang.oes.dataobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Description: oes
 * Created by Rice on 2020/7/3 10:42
 */
@Data
@Entity
@NoArgsConstructor
public class Student {
    /**
     * 学生数据库内部id  关联来自user表的id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @MapsId
    private User user;

    @OneToMany(mappedBy = "student",cascade = CascadeType.REMOVE)
    private List<Student_Exam> studentExams;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime insertTime;

    @Column(columnDefinition = "timestamp default current_timestamp " +
            "on update current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime updateTime;

    @OneToMany(mappedBy = "student",cascade = CascadeType.REMOVE)
    private List<Student_Choice> student_choiceList;

    @OneToMany(mappedBy = "student",cascade = CascadeType.REMOVE)
    private List<Student_Subjective> student_subjectiveList;
}
