package com.dsantos;
public class Main {
    public static void main(String[] args) throws InterruptedException {
        var pool = new ThreadPool(3);
        for (int i = 0; i < 10; i++) {
            int id = i;
            pool.submit(() -> {
                System.out.println("task " + id + " on " + Thread.currentThread().getName());
                Thread.sleep(100);
            });
        }
        Thread.sleep(2000);
        pool.shutdown();
        System.out.println("pool shutdown");
    }
}
