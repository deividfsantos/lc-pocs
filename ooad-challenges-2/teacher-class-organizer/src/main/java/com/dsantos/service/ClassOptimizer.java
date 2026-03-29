package com.dsantos.service;

import com.dsantos.model.Course;
import com.dsantos.model.Room;
import com.dsantos.model.Teacher;
import com.dsantos.repository.CourseRepository;
import com.dsantos.repository.RoomRepository;
import com.dsantos.repository.TeacherRepository;

import java.util.ArrayList;
import java.util.List;

public class ClassOptimizer {

    private final CourseRepository courseRepository;
    private final RoomRepository roomRepository;
    private final TeacherRepository teacherRepository;

    public ClassOptimizer(CourseRepository courseRepository,
                          RoomRepository roomRepository,
                          TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.roomRepository = roomRepository;
        this.teacherRepository = teacherRepository;
    }

    public int optimizeRooms() {
        List<Course> courses = courseRepository.findAll();
        int reassigned = 0;

        for (Course course : courses) {
            if (course.getRoom() == null || course.getTimeSlot() == null) {
                continue;
            }

            int enrolled = course.getEnrolledStudents().size();
            int currentWaste = course.getRoom().getCapacity() - enrolled;

            Room better = findBetterRoom(course, enrolled, currentWaste);
            if (better != null) {
                course.setRoom(better);
                reassigned++;
            }
        }

        return reassigned;
    }

    private Room findBetterRoom(Course course, int enrolled, int currentWaste) {
        List<Room> candidates = roomRepository.findByMinCapacity(enrolled);
        candidates.sort((r1, r2) -> Integer.compare(r1.getCapacity(), r2.getCapacity()));

        for (Room candidate : candidates) {
            if (candidate.getId().equals(course.getRoom().getId())) {
                continue;
            }
            int newWaste = candidate.getCapacity() - enrolled;
            if (newWaste < currentWaste && !isRoomOccupied(candidate, course)) {
                return candidate;
            }
        }
        return null;
    }

    private boolean isRoomOccupied(Room room, Course targetCourse) {
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

    public int balanceTeacherLoad(int maxPerTeacher) {
        List<Teacher> teachers = teacherRepository.findAll();
        int redistributed = 0;

        for (Teacher overloaded : teachers) {
            List<Course> courses = new ArrayList<>(overloaded.getAssignedCourses());
            while (courses.size() > maxPerTeacher) {
                Course toRedistribute = courses.remove(courses.size() - 1);
                Teacher replacement = findReplacementTeacher(overloaded, toRedistribute, teachers);
                if (replacement != null) {
                    overloaded.removeCourse(toRedistribute);
                    toRedistribute.setTeacher(replacement);
                    replacement.assignCourse(toRedistribute);
                    redistributed++;
                }
            }
        }

        return redistributed;
    }

    private Teacher findReplacementTeacher(Teacher exclude, Course course, List<Teacher> teachers) {
        return teachers.stream()
                .filter(t -> !t.getId().equals(exclude.getId()))
                .filter(t -> t.canTeach(course.getSubject()))
                .min((t1, t2) -> Integer.compare(
                        t1.getAssignedCourses().size(),
                        t2.getAssignedCourses().size()))
                .orElse(null);
    }
}

