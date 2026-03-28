package com.dsantos.repository;

import com.dsantos.model.Teacher;
import com.dsantos.model.enums.Subject;

import java.util.List;

public interface TeacherRepository extends Repository<Teacher, String> {
    List<Teacher> findBySubject(Subject subject);
}

