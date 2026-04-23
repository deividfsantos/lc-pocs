package com.dsantos;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
public class ThreadPool {
    private final BlockingQueue<Task> queue = new LinkedBlockingQueue<>();
    private final List<Worker> workers = new ArrayList<>();
    public ThreadPool(int size) {
        for (int i = 0; i < size; i++) {
            var w = new Worker("worker-" + i, queue);
            workers.add(w);
            w.start();
        }
    }
    public void submit(Task task) throws InterruptedException {
        queue.put(task);
    }
}
