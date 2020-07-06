package com.sanguinewang.oes.repository;

import com.sanguinewang.oes.dataobject.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends  BaseReporsitory<Student,Integer> {
}
