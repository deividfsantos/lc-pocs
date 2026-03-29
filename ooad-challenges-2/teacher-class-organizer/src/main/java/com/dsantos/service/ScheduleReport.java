package com.dsantos.service;

import com.dsantos.model.Course;
import com.dsantos.model.Student;
import com.dsantos.model.Teacher;
import com.dsantos.repository.CourseRepository;
import com.dsantos.repository.StudentRepository;
import com.dsantos.repository.TeacherRepository;

import java.util.List;

public class ScheduleReport {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public ScheduleReport(CourseRepository courseRepository,
                          TeacherRepository teacherRepository,
                          StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    public void printFullSchedule() {
        System.out.println("=".repeat(60));
        System.out.println("               FULL SCHEDULE");
        System.out.println("=".repeat(60));

        List<Course> courses = courseRepository.findAll();
        courses.sort((a, b) -> {
            if (a.getTimeSlot() == null) return 1;
            if (b.getTimeSlot() == null) return -1;
            int dayComp = a.getTimeSlot().getDay().compareTo(b.getTimeSlot().getDay());
            if (dayComp != 0) return dayComp;
            return a.getTimeSlot().getStart().compareTo(b.getTimeSlot().getStart());
        });

        for (Course course : courses) {
            System.out.println("-".repeat(60));
            System.out.println("Course   : " + course.getName() + " [" + course.getSubject() + "]");
            System.out.println("Teacher  : " + (course.getTeacher() != null ? course.getTeacher().getName() : "UNASSIGNED"));
            System.out.println("Room     : " + (course.getRoom() != null ? course.getRoom().getName() + " (cap " + course.getRoom().getCapacity() + ")" : "UNASSIGNED"));
            System.out.println("Slot     : " + (course.getTimeSlot() != null ? course.getTimeSlot() : "UNASSIGNED"));
            System.out.println("Enrolled : " + course.getEnrolledStudents().size() + "/" + course.getMaxCapacity());

            if (!course.getEnrolledStudents().isEmpty()) {
                StringBuilder names = new StringBuilder();
                for (Student s : course.getEnrolledStudents()) {
                    if (names.length() > 0) names.append(", ");
                    names.append(s.getName());
                }
                System.out.println("Students : " + names);
            }
        }

        System.out.println("=".repeat(60));
        System.out.println("Total courses: " + courses.size());
        System.out.println("=".repeat(60));
        System.out.println();
    }

    public void printConflicts(List<String> conflicts) {
        System.out.println("=".repeat(60));
        System.out.println("               CONFLICT REPORT");
        System.out.println("=".repeat(60));

        if (conflicts.isEmpty()) {
            System.out.println("No conflicts detected.");
        } else {
            for (String conflict : conflicts) {
                System.out.println("[!] " + conflict);
            }
        }

        System.out.println("=".repeat(60));
        System.out.println("Total conflicts: " + conflicts.size());
        System.out.println("=".repeat(60));
        System.out.println();
    }

    public void printOptimizationSummary() {
        System.out.println("=".repeat(60));
        System.out.println("           OPTIMIZATION SUMMARY");
        System.out.println("=".repeat(60));

        List<Teacher> teachers = teacherRepository.findAll();
        teachers.sort((a, b) -> Integer.compare(b.getAssignedCourses().size(), a.getAssignedCourses().size()));

        System.out.println("Teacher Load:");
        for (Teacher teacher : teachers) {
            System.out.printf("  %-20s -> %d course(s)%n",
                    teacher.getName(), teacher.getAssignedCourses().size());
        }

        System.out.println();
        System.out.println("Room Utilization:");
        for (Course course : courseRepository.findAll()) {
            if (course.getRoom() == null) continue;
            int enrolled = course.getEnrolledStudents().size();
            int capacity = course.getRoom().getCapacity();
            double utilization = capacity > 0 ? (enrolled * 100.0 / capacity) : 0;
            System.out.printf("  %-20s in %-10s -> %d/%d (%.0f%%)%n",
                    course.getName(), course.getRoom().getName(), enrolled, capacity, utilization);
        }

        System.out.println();
        System.out.println("Student Enrollment:");
        List<Student> students = studentRepository.findAll();
        for (Student student : students) {
            System.out.printf("  %-20s -> %d course(s)%n",
                    student.getName(), student.getEnrolledCourses().size());
        }

        System.out.println("=".repeat(60));
        System.out.println();
    }
}

