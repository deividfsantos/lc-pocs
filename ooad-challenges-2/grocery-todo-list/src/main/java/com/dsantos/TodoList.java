package com.dsantos;

import com.dsantos.command.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class TodoList {
    private final List<Todo> todoList;
    private final Stack<Command> undoStack;
    private final Stack<Command> redoStack;

    public TodoList() {
        this.todoList = new ArrayList<>();
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

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
            Todo updatedTodo = new Todo(todo.title(), todo.quantity(), Status.DONE);
            todoList.set(index, updatedTodo);
            return true;
        }
        return false;
    }

    public long countByStatus(Status status) {
        return todoList.stream().filter(t -> t.status() == status).count();
    }

    public int countPending() {
        return (int) countByStatus(Status.PENDING);
    }

    public List<Todo> listPending() {
        return todoList.stream()
                .filter(t -> t.status() == Status.PENDING)
                .collect(Collectors.toList());
    }
}
