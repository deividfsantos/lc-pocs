package com.dsantos.service;

import com.dsantos.model.Course;
import com.dsantos.model.Room;
import com.dsantos.model.Student;
import com.dsantos.model.Teacher;
import com.dsantos.model.TimeSlot;
import com.dsantos.repository.CourseRepository;
import com.dsantos.repository.RoomRepository;
import com.dsantos.repository.StudentRepository;
import com.dsantos.repository.TeacherRepository;

import java.util.List;

public class ClassOrganizer {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final RoomRepository roomRepository;

    public ClassOrganizer(TeacherRepository teacherRepository,
                          StudentRepository studentRepository,
                          CourseRepository courseRepository,
                          RoomRepository roomRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.roomRepository = roomRepository;
    }

    public void assignTeachers() {
        List<Course> unassigned = courseRepository.findUnassigned();
        for (Course course : unassigned) {
            List<Teacher> eligible = teacherRepository.findBySubject(course.getSubject());
            Teacher best = eligible.stream()
                    .min((t1, t2) -> Integer.compare(
                            t1.getAssignedCourses().size(),
                            t2.getAssignedCourses().size()))
                    .orElse(null);

            if (best != null) {
                course.setTeacher(best);
                best.assignCourse(course);
            }
        }
    }

    public void assignRooms(List<TimeSlot> timeSlots) {
        List<Course> courses = courseRepository.findAll();
        List<TimeSlot> slots = new java.util.ArrayList<>(timeSlots);

        for (Course course : courses) {
            if (course.getTimeSlot() == null && !slots.isEmpty()) {
                course.setTimeSlot(slots.remove(0));
            }

            if (course.getRoom() == null) {
                Room suitable = findFreeRoom(course);
                if (suitable != null) {
                    course.setRoom(suitable);
                }
            }
        }
    }

    private Room findFreeRoom(Course course) {
        List<Room> candidates = roomRepository.findByMinCapacity(course.getMaxCapacity());
        candidates.sort((r1, r2) -> Integer.compare(r1.getCapacity(), r2.getCapacity()));

        for (Room candidate : candidates) {
            if (!isRoomOccupied(candidate, course)) {
                return candidate;
            }
        }
        return null;
    }

    private boolean isRoomOccupied(Room room, Course targetCourse) {
        if (targetCourse.getTimeSlot() == null) {
            return false;
        }
        for (Course other : courseRepository.findAll()) {
            if (other.getId().equals(targetCourse.getId())) {
                continue;
            }
            if (other.getRoom() == null || other.getTimeSlot() == null) {
                continue;
            }
            if (other.getRoom().getId().equals(room.getId())
                    && other.getTimeSlot().overlaps(targetCourse.getTimeSlot())) {
                return true;
            }
        }
        return false;
    }

    public void enrollStudents() {
        List<Student> students = studentRepository.findAll();
        List<Course> courses = courseRepository.findAll();

        for (Student student : students) {
            for (Course course : courses) {
                if (course.hasAvailableSeats()
                        && !course.getEnrolledStudents().contains(student)) {
                    course.enrollStudent(student);
                    student.enroll(course);
                    break;
                }
            }
        }
    }
}

