package com.dsantos;

import java.util.ArrayList;
import java.util.List;

public class TodoList {
    private final List<Todo> todoList = new ArrayList<>();

    public void addTodo(Todo todo) {
        todoList.add(todo);
    }

    public List<Todo> getTodos() {
        return todoList;
    }
}
