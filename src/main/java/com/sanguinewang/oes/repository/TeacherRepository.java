package com.sanguinewang.oes.repository;

import com.sanguinewang.oes.dataobject.Teacher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository  extends BaseReporsitory<Teacher,Integer>{


}
