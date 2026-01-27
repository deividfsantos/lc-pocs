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
        Command cmd = new AddCommand(todo);
        cmd.execute();
        undoStack.push(cmd);
        redoStack.clear();
    }

    public List<Todo> getTodos() {
        return todoList;
    }

    public boolean removeTodo(Todo todo) {
        if (!todoList.contains(todo)) return false;
        Command cmd = new RemoveCommand(todo);
        cmd.execute();
        undoStack.push(cmd);
        redoStack.clear();
        return true;
    }

    public boolean markAsDone(Todo todo) {
        int index = todoList.indexOf(todo);
        if (index != -1) {
            Command cmd = new MarkAsDoneCommand(index, todo);
            cmd.execute();
            undoStack.push(cmd);
            redoStack.clear();
            return true;
        }
        return false;
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Command cmd = undoStack.pop();
            cmd.undo();
            redoStack.push(cmd);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command cmd = redoStack.pop();
            cmd.execute();
            undoStack.push(cmd);
        }
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

    private class AddCommand implements Command {
        private final Todo todo;

        public AddCommand(Todo todo) {
            this.todo = todo;
        }

        @Override
        public void execute() {
            todoList.add(todo);
        }

        @Override
        public void undo() {
            todoList.remove(todo);
        }
    }

    private class RemoveCommand implements Command {
        private final Todo todo;
        private int index = -1;

        public RemoveCommand(Todo todo) {
            this.todo = todo;
        }

        @Override
        public void execute() {
            index = todoList.indexOf(todo);
            todoList.remove(todo);
        }

        @Override
        public void undo() {
            if (index >= 0 && index <= todoList.size()) {
                todoList.add(index, todo);
            } else {
                todoList.add(todo);
            }
        }
    }

    private class MarkAsDoneCommand implements Command {
        private final int index;
        private final Todo originalTodo;

        public MarkAsDoneCommand(int index, Todo todo) {
            this.index = index;
            this.originalTodo = todo;
        }

        @Override
        public void execute() {
            Todo doneTodo = new Todo(originalTodo.title(), originalTodo.quantity(), Status.DONE);
            todoList.set(index, doneTodo);
        }

        @Override
        public void undo() {
            todoList.set(index, originalTodo);
        }
    }
}
