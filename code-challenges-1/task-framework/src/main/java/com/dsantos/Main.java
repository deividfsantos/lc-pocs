package com.dsantos;
import java.util.concurrent.LinkedBlockingQueue;
public class Main {
    public static void main(String[] args) throws InterruptedException {
        var queue = new LinkedBlockingQueue<Task>();
        var worker = new Worker("worker-0", queue);
        worker.start();
        queue.put(() -> System.out.println("task 1 on " + Thread.currentThread().getName()));
        queue.put(() -> System.out.println("task 2 on " + Thread.currentThread().getName()));
        Thread.sleep(500);
        worker.interrupt();
    }
}
