package com.dsantos;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        var todoList = new TodoList();
        var todo1 = new Todo("Buy Snacks", 5, Status.PENDING);
        var todo2 = new Todo("Buy Rice", 5, Status.PENDING);
        todoList.addTodo(todo1);
        todoList.addTodo(todo2);

        todoList.getTodos().forEach(System.out::println);
    }
}
