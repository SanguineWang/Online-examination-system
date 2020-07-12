package com.sanguinewang.oes.VO;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author SanguineWang
 * @Date 2020-07-12 16:54
 */
@Data
public class TeacherExamVO {
    private Integer id;
    // 开始时间
    private LocalDateTime startTime;
    // 结束时间
    private LocalDateTime endTime;
    private String name;//考试名
    private Integer studentName;//考生数
    private String TeacherName;//教师名

}
