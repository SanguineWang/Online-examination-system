package com.sanguinewang.oes.controller;

import com.sanguinewang.oes.dataobject.Exam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Description: oes
 * Created by Rice on 2020/7/6 9:13
 */
@RestController
@Slf4j
@RequestMapping("/api/students/")
public class StudentController {

    @ApiOperation("查询自己的老师")
    @GetMapping("teacher")
    public Map<String, String> getExam() {
        return Map.of("Exam", "Test-String");
    }
}
