package com.sanguinewang.oes.dataobject;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Description
 * @Author SanguineWang
 * @Date 2020-07-03 15:47
 */
@Data
@Entity
@NoArgsConstructor
public class Student_Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Student student;

    //题目
    @ManyToOne
    private Choice choice;


    //学生的答案
    private Integer answer;

}
