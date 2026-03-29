package com.dsantos.service;

import com.dsantos.model.Course;
import com.dsantos.model.Student;
import com.dsantos.model.Teacher;
import com.dsantos.repository.CourseRepository;

import java.util.ArrayList;
import java.util.List;

public class ConflictChecker {

    private final CourseRepository courseRepository;

    public ConflictChecker(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<String> check() {
        List<String> conflicts = new ArrayList<>();
        List<Course> courses = courseRepository.findAll();

        for (int i = 0; i < courses.size(); i++) {
            for (int j = i + 1; j < courses.size(); j++) {
                Course a = courses.get(i);
                Course b = courses.get(j);

                if (a.getTimeSlot() == null || b.getTimeSlot() == null) {
                    continue;
                }

                if (!a.getTimeSlot().overlaps(b.getTimeSlot())) {
                    continue;
                }

                checkTeacherConflict(a, b, conflicts);
                checkRoomConflict(a, b, conflicts);
                checkStudentConflicts(a, b, conflicts);
            }
        }

        return conflicts;
    }

    private void checkTeacherConflict(Course a, Course b, List<String> conflicts) {
        if (a.getTeacher() == null || b.getTeacher() == null) {
            return;
        }
        Teacher ta = a.getTeacher();
        Teacher tb = b.getTeacher();
        if (ta.getId().equals(tb.getId())) {
            conflicts.add("TEACHER CONFLICT: " + ta.getName()
                    + " is assigned to both '" + a.getName() + "' and '" + b.getName()
                    + "' at " + a.getTimeSlot());
        }
    }

    private void checkRoomConflict(Course a, Course b, List<String> conflicts) {
        if (a.getRoom() == null || b.getRoom() == null) {
            return;
        }
        if (a.getRoom().getId().equals(b.getRoom().getId())) {
            conflicts.add("ROOM CONFLICT: " + a.getRoom().getName()
                    + " is booked for both '" + a.getName() + "' and '" + b.getName()
                    + "' at " + a.getTimeSlot());
        }
    }

    private void checkStudentConflicts(Course a, Course b, List<String> conflicts) {
        for (Student student : a.getEnrolledStudents()) {
            for (Student other : b.getEnrolledStudents()) {
                if (student.getId().equals(other.getId())) {
                    conflicts.add("STUDENT CONFLICT: " + student.getName()
                            + " is enrolled in both '" + a.getName() + "' and '" + b.getName()
                            + "' at " + a.getTimeSlot());
                }
            }
        }
    }
}

