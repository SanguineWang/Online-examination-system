package com.sanguinewang.oes.service;

import com.sanguinewang.oes.component.RequestComponent;
import com.sanguinewang.oes.dataobject.*;
import com.sanguinewang.oes.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Description
 * @Author Tan
 * @Date 2020-07-07 8:05
 */
@Service("StudentService")
@Transactional
@Slf4j
public class StudentService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    Student_ExamRepository student_examRepository;
    @Autowired
    ExamRepository examRepository;
    @Autowired
    ChoiceRepository choiceRepository;
    @Autowired
    JudgmentRepositry judgmentRepositry;
    @Autowired
    SubjectiveRepository subjectiveRepository;
    @Autowired
    Student_ChoiceRepository student_choiceRepository;
    @Autowired
    Student_JudgmentRepository student_judgmentRepository;
    @Autowired
    Student_SubjectiveRepository student_subjectiveRepository;

    public Student findStudentByUid(Integer uid) {
        Student student = studentRepository.findById(uid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "当前用户不存在"));

        return student;
    }

    /**
     * 判断学生是否能参加当前考试
     *
     * @param examId
     * @param uid
     * @return
     */
    public Boolean VerifyJoinability(Integer examId, Integer uid) {
        Exam exam = studentRepository.findExamByExamIdAndTime(examId, LocalDateTime.now())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "当前考试不存在或时间不符合"));
        Student_Exam student_exam = student_examRepository.findExamByStudentUidAndExamId(uid, examId)
                .filter(e -> !e.isSubmit())//过滤当前考试是否已经提交
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "当前考试不存在或已提交"));
        if (exam != null && student_exam != null) {
            return true;
        } else {
            return false;
        }
    }

    public Exam getExam(Integer eid) {
        return examRepository.findExamByExamId(eid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "当前考试不存在"));
    }


    public void submitExam(Integer examId, Integer uid) {

        Student_Exam student_exam = student_examRepository.findExamByStudentUidAndExamId(uid, examId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "当前学生-考试不存在"));
        student_exam.setSubmit(true);
        student_examRepository.save(student_exam);
//        student_examRepository.updateStudent_ExamSubmitStatus(uid, examId);
    }

    /**
     * 保存学生的选择题的选项
     *
     * @param choiceId 当前保存题目的id
     * @param student  写出该答案的学生
     * @param answer   该学生回答的答案
     */
    public void saveChoice(Integer choiceId, Student student, Integer answer) {
        Student_Choice student_choice = new Student_Choice();
        Choice choice = choiceRepository.findById(choiceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "当前题目不存在"));
        student_choice.setChoice(choice);
        student_choice.setStudent(student);
        student_choice.setAnswer(answer);
        if (choice.getAnswer() == answer)
            student_choice.setScore(choice.getScore());
        else student_choice.setScore((float) 0);
        student_choiceRepository.save(student_choice);
    }

    public void saveJudgment(Integer JudgmentId, Student studentFromDB, Integer answer) {
        Student_Judgment student_judgment = new Student_Judgment();
        Judgment judgment = judgmentRepositry.findById(JudgmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "当前题目不存在"));
        student_judgment.setJudgment(judgment);
        student_judgment.setStudent(studentFromDB);
        student_judgment.setAnswer(answer);
        if (judgment.getAnswer() == answer)
            student_judgment.setScore(judgment.getScore());
        else student_judgment.setScore((float) 0);
        student_judgmentRepository.save(student_judgment);

    }

    public void saveSubject(Integer SubjectiveId, Student studentFromDB, String answer) {
        Student_Subjective student_subjective = new Student_Subjective();
        Subjective subjective = subjectiveRepository.findById(SubjectiveId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "当前题目不存在"));
        student_subjective.setSubjective(subjective);
        student_subjective.setStudent(studentFromDB);
        student_subjective.setAnswer(answer);
        student_subjectiveRepository.save(student_subjective);
    }

    public List<Student_Exam> listGrade(Integer studentId) {
        List<Student_Exam> examListByStudentUid = student_examRepository.findExamListByStudentUid(studentId);
        List<Student_Exam> examList2Controller = new ArrayList<>();
        examListByStudentUid.forEach(student_exam -> {
            Float objectiveGrade = student_exam.getObjectiveGrade();
            Float subjectiveGrade = student_exam.getSubjectiveGrade();
            if (objectiveGrade != null && subjectiveGrade != null) {
                examList2Controller.add(student_exam);
            }
            //如果客观题分数为空 则直接进行计算
            if (objectiveGrade == null) {
                calculateObject(student_exam.getExam().getId(), studentId);
            }
        });
        return examList2Controller;
    }

    /**
     * 计算客观题分数并保存在数据库内后返回
     * 如果存在则直接返回
     *
     * @param examId
     * @param uid
     * @return
     */
    public Float calculateObject(Integer examId, Integer uid) {
        Student_Exam examByStudentUidAndExamId = student_examRepository.findExamByStudentUidAndExamId(uid, examId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "当前考试不存在"));
        Float grade = Float.valueOf(0);
        if (examByStudentUidAndExamId.getObjectiveGrade() != null) {
            return examByStudentUidAndExamId.getObjectiveGrade();
        }
        //计算选择题分数
        for (Float f : student_choiceRepository.listScoreByStudentIdAndExamId(uid, examId)) {
            grade += f;
        }
        //计算判断题分数
        for (Float f : student_judgmentRepository.listScoreByStudentIdAndExamId(uid, examId)) {
            grade += f;
        }
        examByStudentUidAndExamId.setObjectiveGrade(grade);
        student_examRepository.save(examByStudentUidAndExamId);
        return grade;
    }
}
