package com.dsantos;
public class Main {
    public static void main(String[] args) {
        Task t = () -> System.out.println("hello from task");
        try {
            t.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
