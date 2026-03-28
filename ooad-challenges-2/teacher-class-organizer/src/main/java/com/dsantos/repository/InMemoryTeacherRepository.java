package com.dsantos.repository;

import com.dsantos.model.Teacher;
import com.dsantos.model.enums.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryTeacherRepository implements TeacherRepository {

    private final Map<String, Teacher> store = new HashMap<>();

    @Override
    public void save(Teacher teacher) {
        store.put(teacher.getId(), teacher);
    }

    @Override
    public Optional<Teacher> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Teacher> findAll() {
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
    public List<Teacher> findBySubject(Subject subject) {
        List<Teacher> result = new ArrayList<>();
        for (Teacher teacher : store.values()) {
            if (teacher.canTeach(subject)) {
                result.add(teacher);
            }
        }
        return result;
    }
}

