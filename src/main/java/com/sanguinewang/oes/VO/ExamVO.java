package com.sanguinewang.oes.VO;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.text.StyledEditorKit;
import java.time.LocalDateTime;

/**
 * Description: oes
 * Created by Rice on 2020/7/10 23:47
 * 用于封装考试列表的ExamVO
 */
@Data
@NoArgsConstructor
public class ExamVO {

    private Integer id;
    // 开始时间
    private LocalDateTime startTime;
    // 结束时间
    private LocalDateTime endTime;
    private String name;//考试名
    private String studentName;//考生名
    private String TeacherName;//教师名
    private Boolean isSubmit;//教师名
    private Integer TeacherNumber;
    private Integer StudentNumber;

    private Float objectiveGrade;
    private Float subjectiveGrade;

    public ExamVO(Integer id, String name, LocalDateTime startTime, LocalDateTime endTime, Boolean isSubmit, String studentName, String teacherName, Integer teacherNumber, Integer studentNumber) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.studentName = studentName;
        this.isSubmit=isSubmit;
        this.TeacherName = teacherName;
        this.TeacherNumber = teacherNumber;
        this.StudentNumber = studentNumber;
    }
}
