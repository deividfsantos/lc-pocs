package com.dsantos;

import com.dsantos.model.*;
import com.dsantos.model.enums.SchoolDay;
import com.dsantos.model.enums.Subject;
import com.dsantos.repository.*;
import com.dsantos.service.ClassOptimizer;
import com.dsantos.service.ClassOrganizer;
import com.dsantos.service.ConflictChecker;
import com.dsantos.service.ScheduleReport;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        TeacherRepository teacherRepo = new InMemoryTeacherRepository();
        StudentRepository studentRepo = new InMemoryStudentRepository();
        CourseRepository courseRepo = new InMemoryCourseRepository();
        RoomRepository roomRepo = new InMemoryRoomRepository();

        populateTeachers(teacherRepo);
        populateStudents(studentRepo);
        populateCourses(courseRepo);
        populateRooms(roomRepo);

        List<TimeSlot> timeSlots = List.of(
                new TimeSlot(SchoolDay.MONDAY, LocalTime.of(8, 0), LocalTime.of(9, 30)),
                new TimeSlot(SchoolDay.MONDAY, LocalTime.of(10, 0), LocalTime.of(11, 30)),
                new TimeSlot(SchoolDay.TUESDAY, LocalTime.of(8, 0), LocalTime.of(9, 30)),
                new TimeSlot(SchoolDay.TUESDAY, LocalTime.of(10, 0), LocalTime.of(11, 30)),
                new TimeSlot(SchoolDay.WEDNESDAY, LocalTime.of(8, 0), LocalTime.of(9, 30)),
                new TimeSlot(SchoolDay.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(14, 30)),
                new TimeSlot(SchoolDay.THURSDAY, LocalTime.of(8, 0), LocalTime.of(9, 30)),
                new TimeSlot(SchoolDay.FRIDAY, LocalTime.of(8, 0), LocalTime.of(9, 30))
        );

        ClassOrganizer organizer = new ClassOrganizer(teacherRepo, studentRepo, courseRepo, roomRepo);
        ClassOptimizer optimizer = new ClassOptimizer(courseRepo, roomRepo, teacherRepo);
        ConflictChecker checker = new ConflictChecker(courseRepo);
        ScheduleReport report = new ScheduleReport(courseRepo, teacherRepo, studentRepo);

        System.out.println(">>> Step 1: Assigning teachers to courses...");
        organizer.assignTeachers();

        System.out.println(">>> Step 2: Assigning rooms and time slots...");
        organizer.assignRooms(new java.util.ArrayList<>(timeSlots));

        System.out.println(">>> Step 3: Enrolling students across courses...");
        enrollStudentsManually(courseRepo, studentRepo);

        System.out.println(">>> Step 4: Injecting intentional double-booking conflict...");
        injectConflict(courseRepo);

        System.out.println(">>> Step 5: Checking conflicts before optimization...");
        List<String> conflictsBefore = checker.check();
        report.printConflicts(conflictsBefore);

        System.out.println(">>> Step 6: Printing full schedule before optimization...");
        report.printFullSchedule();

        System.out.println(">>> Step 7: Optimizing room assignments...");
        int roomsReassigned = optimizer.optimizeRooms();
        System.out.println("    Rooms reassigned: " + roomsReassigned);

        System.out.println(">>> Step 8: Balancing teacher load (max 2 courses per teacher)...");
        int redistributed = optimizer.balanceTeacherLoad(2);
        System.out.println("    Courses redistributed: " + redistributed);

        System.out.println(">>> Step 9: Checking conflicts after optimization...");
        List<String> conflictsAfter = checker.check();
        report.printConflicts(conflictsAfter);

        System.out.println(">>> Step 10: Printing optimization summary...");
        report.printOptimizationSummary();

        System.out.println(">>> Step 11: Printing final full schedule...");
        report.printFullSchedule();
    }

    private static void populateTeachers(TeacherRepository repo) {
        repo.save(new Teacher("Alice Martin", "alice@school.edu", Set.of(Subject.MATH, Subject.PHYSICS)));
        repo.save(new Teacher("Bob Chen", "bob@school.edu", Set.of(Subject.COMPUTER_SCIENCE, Subject.MATH)));
        repo.save(new Teacher("Clara Osei", "clara@school.edu", Set.of(Subject.BIOLOGY, Subject.CHEMISTRY)));
        repo.save(new Teacher("David Patel", "david@school.edu", Set.of(Subject.HISTORY, Subject.LITERATURE)));
        repo.save(new Teacher("Elena Russo", "elena@school.edu", Set.of(Subject.ENGLISH, Subject.LITERATURE)));
    }

    private static void populateStudents(StudentRepository repo) {
        repo.save(new Student("Tom Walker", "tom@school.edu"));
        repo.save(new Student("Sara Lee", "sara@school.edu"));
        repo.save(new Student("James Brown", "james@school.edu"));
        repo.save(new Student("Nina Gomez", "nina@school.edu"));
        repo.save(new Student("Liam Scott", "liam@school.edu"));
        repo.save(new Student("Mia Zhang", "mia@school.edu"));
        repo.save(new Student("Noah King", "noah@school.edu"));
        repo.save(new Student("Ella Davis", "ella@school.edu"));
        repo.save(new Student("Oliver White", "oliver@school.edu"));
        repo.save(new Student("Ava Johnson", "ava@school.edu"));
    }

    private static void populateCourses(CourseRepository repo) {
        repo.save(new Course("Calculus I", Subject.MATH, 30));
        repo.save(new Course("Intro to Physics", Subject.PHYSICS, 25));
        repo.save(new Course("World History", Subject.HISTORY, 28));
        repo.save(new Course("English Composition", Subject.ENGLISH, 20));
        repo.save(new Course("Data Structures", Subject.COMPUTER_SCIENCE, 22));
        repo.save(new Course("Cell Biology", Subject.BIOLOGY, 18));
        repo.save(new Course("Organic Chemistry", Subject.CHEMISTRY, 15));
        repo.save(new Course("Modern Literature", Subject.LITERATURE, 24));
    }

    private static void populateRooms(RoomRepository repo) {
        repo.save(new Room("Room 101", 35));
        repo.save(new Room("Room 102", 20));
        repo.save(new Room("Room 103", 25));
        repo.save(new Room("Lab A", 18));
        repo.save(new Room("Hall B", 50));
    }

    private static void enrollStudentsManually(CourseRepository courseRepo, StudentRepository studentRepo) {
        List<Course> courses = courseRepo.findAll();
        courses.sort((a, b) -> a.getName().compareTo(b.getName()));
        List<Student> students = studentRepo.findAll();

        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            enrollIfPossible(student, courses.get(i % courses.size()));
            enrollIfPossible(student, courses.get((i + 2) % courses.size()));
            enrollIfPossible(student, courses.get((i + 4) % courses.size()));
        }
    }

    private static void enrollIfPossible(Student student, Course course) {
        if (course.hasAvailableSeats() && !course.getEnrolledStudents().contains(student)) {
            course.enrollStudent(student);
            student.enroll(course);
        }
    }

    private static void injectConflict(CourseRepository courseRepo) {
        List<Course> courses = courseRepo.findAll().stream()
                .filter(c -> c.getTimeSlot() != null && c.getTeacher() != null)
                .sorted((a, b) -> a.getName().compareTo(b.getName()))
                .collect(java.util.stream.Collectors.toList());

        if (courses.size() < 2) {
            return;
        }

        Course first = courses.get(0);
        Course second = courses.get(1);

        second.setTimeSlot(first.getTimeSlot());

        Teacher conflicting = first.getTeacher();
        second.setTeacher(conflicting);
        conflicting.assignCourse(second);
    }
}

