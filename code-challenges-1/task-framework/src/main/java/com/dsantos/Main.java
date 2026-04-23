package com.dsantos;
public class Main {
    public static void main(String[] args) throws InterruptedException {
        var pool = new ThreadPool(3);
        for (int i = 0; i < 5; i++) {
            int id = i;
            pool.submit(() -> System.out.println("task " + id + " on " + Thread.currentThread().getName()));
        }
        Thread.sleep(1000);
    }
}
