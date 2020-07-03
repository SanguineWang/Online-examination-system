package com.sanguinewang.oes.dataobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 考试
 * @Author SanguineWang
 * @Date 2020-07-03 14:17
 */
@Data
@Entity
@NoArgsConstructor
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // 开始时间
    private LocalDateTime startTime;
    // 结束时间
    private LocalDateTime endTime;
    // 属于的老师 多个考试可以对应一个老师
    @ManyToOne
    @ToString.Exclude
    private Teacher teacher;

    //考试的学生
    @OneToMany(mappedBy = "exam", cascade = CascadeType.REMOVE)
    private List<Student_Exam> studentExams;

    //选择题列表 一个考试对应多个选择题
    @OneToMany(mappedBy = "exam", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private List<Choice> choiceList;
    //判断题列表 一个考试对应多个判断题
    @OneToMany(mappedBy = "exam", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private List<Judgment> judgmentList;


    //主观题列表 一个考试对应多个主观题
    @OneToMany(mappedBy = "exam", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private List<Subjective> subjectiveList;

    /**
     * 自动插入时间
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime insertTime;
}
