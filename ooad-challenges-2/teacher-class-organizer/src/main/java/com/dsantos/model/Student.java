package com.dsantos.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student extends Person {

    private final List<Course> enrolledCourses;

    public Student(String name, String email) {
        super(name, email);
        this.enrolledCourses = new ArrayList<>();
    }

    public void enroll(Course course) {
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
        }
    }

    public void drop(Course course) {
        enrolledCourses.remove(course);
    }

    public List<Course> getEnrolledCourses() {
        return Collections.unmodifiableList(enrolledCourses);
    }
}

