package com.dsantos;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TodoListTest {

    @Test
    void addAndGetTodos() {
        TodoList todoList = new TodoList();
        assertTrue(todoList.getTodos().isEmpty());

        Todo t1 = new Todo("Buy Snacks", 5, Status.PENDING);
        Todo t2 = new Todo("Buy Rice", 2, Status.PENDING);

        todoList.addTodo(t1);
        todoList.addTodo(t2);

        List<Todo> todos = todoList.getTodos();
        assertEquals(2, todos.size());
        assertTrue(todos.contains(t1));
        assertTrue(todos.contains(t2));
    }

    @Test
    void removeTodo() {
        TodoList todoList = new TodoList();
        Todo t = new Todo("Milk", 1, Status.PENDING);
        todoList.addTodo(t);

        assertTrue(todoList.removeTodo(t));
        assertFalse(todoList.getTodos().contains(t));

        Todo other = new Todo("Eggs", 12, Status.PENDING);
        assertFalse(todoList.removeTodo(other));
    }

    @Test
    void markAsDoneWhenPresent() {
        TodoList todoList = new TodoList();
        Todo t = new Todo("Butter", 1, Status.PENDING);
        todoList.addTodo(t);

        boolean result = todoList.markAsDone(t);
        assertTrue(result);

        List<Todo> todos = todoList.getTodos();
        assertEquals(1, todos.size());
        Todo updated = todos.get(0);
        assertEquals("Butter", updated.title());
        assertEquals(1, updated.quantity());
        assertEquals(Status.DONE, updated.status());
    }

    @Test
    void markAsDoneWhenNotPresent() {
        TodoList todoList = new TodoList();
        Todo t = new Todo("Jam", 1, Status.PENDING);
        assertFalse(todoList.markAsDone(t));
    }
}

