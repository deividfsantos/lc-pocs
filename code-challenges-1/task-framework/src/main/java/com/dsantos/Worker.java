package com.dsantos;
import java.util.concurrent.BlockingQueue;
public class Worker extends Thread {
    private final BlockingQueue<Task> queue;
    public Worker(String name, BlockingQueue<Task> queue) {
        super(name);
        this.queue = queue;
    }
    @Override
    public void run() {
        while (true) {
            try {
                Task task = queue.take();
                task.execute();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                System.err.println("Task error: " + e.getMessage());
            }
        }
    }
}
