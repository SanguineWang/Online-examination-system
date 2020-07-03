package com.sanguinewang.oes.dataobject;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Description
 * @Author SanguineWang
 * @Date 2020-07-03 15:58
 */
@Data
@Entity
@NoArgsConstructor
public class Student_Subjective {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Student student;

    // 题目
    @ManyToOne
    private Subjective subjective;

    private String answer;

}
