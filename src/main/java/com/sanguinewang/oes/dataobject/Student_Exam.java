package com.sanguinewang.oes.dataobject;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * @Description
 * @Author SanguineWang
 * @Date 2020-07-03 15:10
 */
@Entity
@Data
@NoArgsConstructor
public class Student_Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @ToString.Exclude
    private Student student;

    @ManyToOne
    @ToString.Exclude
    private Exam exam;

    

    private Float choiceGrade;
    private Float subjectiveGrade;



}
