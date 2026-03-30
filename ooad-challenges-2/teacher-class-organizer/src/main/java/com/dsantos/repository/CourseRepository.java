package com.dsantos.repository;

import com.dsantos.model.Course;
import com.dsantos.model.enums.Subject;

import java.util.List;

public interface CourseRepository extends Repository<Course, String> {
    List<Course> findBySubject(Subject subject);

    List<Course> findUnassigned();
}

