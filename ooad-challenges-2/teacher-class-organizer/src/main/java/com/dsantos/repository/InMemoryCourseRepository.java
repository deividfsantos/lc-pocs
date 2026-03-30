package com.dsantos.repository;

import com.dsantos.model.Course;
import com.dsantos.model.enums.Subject;

import java.util.*;

public class InMemoryCourseRepository implements CourseRepository {

    private final Map<String, Course> store = new HashMap<>();

    @Override
    public void save(Course course) {
        store.put(course.getId(), course);
    }

    @Override
    public Optional<Course> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Course> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteById(String id) {
        store.remove(id);
    }

    @Override
    public int count() {
        return store.size();
    }

    @Override
    public List<Course> findBySubject(Subject subject) {
        List<Course> result = new ArrayList<>();
        for (Course course : store.values()) {
            if (course.getSubject() == subject) {
                result.add(course);
            }
        }
        return result;
    }

    @Override
    public List<Course> findUnassigned() {
        List<Course> result = new ArrayList<>();
        for (Course course : store.values()) {
            if (course.getTeacher() == null) {
                result.add(course);
            }
        }
        return result;
    }
}

