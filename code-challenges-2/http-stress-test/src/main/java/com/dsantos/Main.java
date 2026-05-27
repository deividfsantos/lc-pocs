package com.dsantos;
import com.dsantos.config.StressTestConfig;
import com.dsantos.worker.RequestWorker;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
public class Main {
    public static void main(String[] args) throws InterruptedException {
        StressTestConfig config = new StressTestConfig(
                "https://httpbin.org/get", "GET", 5, 20, 10);
        ExecutorService executor = Executors.newFixedThreadPool(config.getConcurrency());
        for (int i = 0; i < config.getTotalRequests(); i++) {
            executor.submit(new RequestWorker(config));
        }
        executor.shutdown();
        executor.awaitTermination(60, TimeUnit.SECONDS);
        System.out.println("Done.");
    }
}
