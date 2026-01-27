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
        Todo updated = todos.getFirst();
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

    @Test
    void countPendingWorks() {
        TodoList todoList = new TodoList();
        todoList.addTodo(new Todo("A", 1, Status.PENDING));
        todoList.addTodo(new Todo("B", 2, Status.DONE));
        todoList.addTodo(new Todo("C", 3, Status.PENDING));

        assertEquals(2, todoList.countPending());
        assertEquals(1, todoList.countByStatus(Status.DONE));
    }

    @Test
    void undoAddTodoRemovesIt() {
        TodoList todoList = new TodoList();
        Todo t = new Todo("Undo Test", 1, Status.PENDING);
        todoList.addTodo(t);
        todoList.undo();
        assertFalse(todoList.getTodos().contains(t));
    }

    @Test
    void redoAddTodoRestoresIt() {
        TodoList todoList = new TodoList();
        Todo t = new Todo("Redo Test", 1, Status.PENDING);
        todoList.addTodo(t);
        todoList.undo();
        todoList.redo();
        assertTrue(todoList.getTodos().contains(t));
    }

    @Test
    void undoRemoveTodoRestoresIt() {
        TodoList todoList = new TodoList();
        Todo t = new Todo("Remove Undo", 1, Status.PENDING);
        todoList.addTodo(t);
        todoList.removeTodo(t);
        todoList.undo();
        assertTrue(todoList.getTodos().contains(t));
    }

    @Test
    void redoRemoveTodoRemovesItAgain() {
        TodoList todoList = new TodoList();
        Todo t = new Todo("Remove Redo", 1, Status.PENDING);
        todoList.addTodo(t);
        todoList.removeTodo(t);
        todoList.undo();
        todoList.redo();
        assertFalse(todoList.getTodos().contains(t));
    }

    @Test
    void undoMarkAsDoneRestoresPending() {
        TodoList todoList = new TodoList();
        Todo t = new Todo("Undo Done", 1, Status.PENDING);
        todoList.addTodo(t);
        todoList.markAsDone(t);
        todoList.undo();
        Todo restored = todoList.getTodos().getFirst();
        assertEquals(Status.PENDING, restored.status());
    }

    @Test
    void redoMarkAsDoneSetsDoneAgain() {
        TodoList todoList = new TodoList();
        Todo t = new Todo("Redo Done", 1, Status.PENDING);
        todoList.addTodo(t);
        todoList.markAsDone(t);
        todoList.undo();
        todoList.redo();
        Todo done = todoList.getTodos().getFirst();
        assertEquals(Status.DONE, done.status());
    }

    @Test
    void undoRedoSequenceWorks() {
        TodoList todoList = new TodoList();
        var t1 = new Todo("A", 1, Status.PENDING);
        var t2 = new Todo("B", 2, Status.PENDING);
        todoList.addTodo(t1);
        todoList.addTodo(t2);
        todoList.markAsDone(t1);
        todoList.removeTodo(t2);
        todoList.undo();
        todoList.undo();
        var todos = todoList.getTodos();
        assertEquals(2, todos.size());
        assertTrue(todos.stream().anyMatch(t -> t.title().equals("A") && t.status() == Status.PENDING));
        assertTrue(todos.stream().anyMatch(t -> t.title().equals("B")));
        todoList.redo();
        todoList.redo();
        var todos2 = todoList.getTodos();
        assertEquals(1, todos2.size());
        assertEquals("A", todos2.getFirst().title());
        assertEquals(Status.DONE, todos2.getFirst().status());
    }
}
