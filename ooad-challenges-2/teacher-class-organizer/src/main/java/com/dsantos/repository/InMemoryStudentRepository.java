package com.dsantos.repository;

import com.dsantos.model.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryStudentRepository implements StudentRepository {

    private final Map<String, Student> store = new HashMap<>();

    @Override
    public void save(Student student) {
        store.put(student.getId(), student);
    }

    @Override
    public Optional<Student> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Student> findAll() {
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
}

