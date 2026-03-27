package com.dsantos.model;

import com.dsantos.model.enums.Subject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Course implements Schedulable {

    private final String id;
    private String name;
    private final Subject subject;
    private Teacher teacher;
    private Room room;
    private TimeSlot timeSlot;
    private final List<Student> enrolledStudents;
    private final int maxCapacity;

    public Course(String name, Subject subject, int maxCapacity) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("Max capacity must be positive");
        }
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.subject = subject;
        this.maxCapacity = maxCapacity;
        this.enrolledStudents = new ArrayList<>();
    }

    public void enrollStudent(Student student) {
        if (enrolledStudents.size() >= maxCapacity) {
            throw new IllegalStateException("Course '" + name + "' is at full capacity");
        }
        if (!enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
        }
    }

    public void removeStudent(Student student) {
        enrolledStudents.remove(student);
    }

    public boolean isFullyAssigned() {
        return teacher != null && room != null && timeSlot != null;
    }

    public boolean hasAvailableSeats() {
        return enrolledStudents.size() < maxCapacity;
    }

    public int getAvailableSeats() {
        return maxCapacity - enrolledStudents.size();
    }

    @Override
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    @Override
    public Room getRoom() {
        return room;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Subject getSubject() {
        return subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public List<Student> getEnrolledStudents() {
        return Collections.unmodifiableList(enrolledStudents);
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    @Override
    public String toString() {
        return "Course{name='" + name + "', subject=" + subject + ", enrolled=" + enrolledStudents.size() + "/" + maxCapacity + "}";
    }
}

