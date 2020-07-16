package com.sanguinewang.oes.VO;

import com.sanguinewang.oes.dataobject.Exam;
import com.sanguinewang.oes.dataobject.Student;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author SanguineWang
 * @Date 2020-07-16 13:26
 */
@Data
@NoArgsConstructor
public class GradeSheetVO {
    private Student student;
    private String exam;
    private String teacher;
    private Float Grade;
}
