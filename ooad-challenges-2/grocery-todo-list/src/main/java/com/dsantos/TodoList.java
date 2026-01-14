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

    public boolean removeTodo(Todo todo) {
        return todoList.remove(todo);
    }

    public boolean markAsDone(Todo todo) {
        int index = todoList.indexOf(todo);
        if (index != -1) {
            Todo updatedTodo = new Todo(todo.title(), todo.description(), Status.DONE);
            todoList.set(index, updatedTodo);
            return true;
        }
        return false;
    }
}
