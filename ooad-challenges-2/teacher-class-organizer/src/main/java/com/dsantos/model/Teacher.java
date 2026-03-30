package com.dsantos.model;

import com.dsantos.model.enums.Subject;

import java.util.*;

public class Teacher extends Person {

    private final Set<Subject> expertise;
    private final List<Course> assignedCourses;

    public Teacher(String name, String email, Set<Subject> expertise) {
        super(name, email);
        this.expertise = new HashSet<>(expertise);
        this.assignedCourses = new ArrayList<>();
    }

    public boolean canTeach(Subject subject) {
        return expertise.contains(subject);
    }

    public void assignCourse(Course course) {
        if (!assignedCourses.contains(course)) {
            assignedCourses.add(course);
        }
    }

    public void removeCourse(Course course) {
        assignedCourses.remove(course);
    }

    public Set<Subject> getExpertise() {
        return Collections.unmodifiableSet(expertise);
    }

    public List<Course> getAssignedCourses() {
        return Collections.unmodifiableList(assignedCourses);
    }
}

