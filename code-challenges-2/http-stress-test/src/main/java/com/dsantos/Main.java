package com.dsantos;
import com.dsantos.config.StressTestConfig;
import com.dsantos.stats.StatsCollector;
import com.dsantos.worker.RequestWorker;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
public class Main {
    public static void main(String[] args) throws InterruptedException {
        StressTestConfig config = new StressTestConfig(
                "https://httpbin.org/get", "GET", 10, 50, 10);
        StatsCollector stats = new StatsCollector();
        ExecutorService executor = Executors.newFixedThreadPool(config.getConcurrency());
        long start = System.currentTimeMillis();
        for (int i = 0; i < config.getTotalRequests(); i++) {
            executor.submit(new RequestWorker(config, stats));
        }
        executor.shutdown();
        executor.awaitTermination(120, TimeUnit.SECONDS);
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("Completed in " + elapsed + " ms");
        System.out.println("Success: " + stats.getSuccessCount());
        System.out.println("Failures: " + stats.getFailureCount());
        System.out.printf("Avg latency: %.2f ms%n", stats.getAverageDurationMillis());
        System.out.println("Min latency: " + stats.getMinDurationMillis() + " ms");
        System.out.println("Max latency: " + stats.getMaxDurationMillis() + " ms");
    }
}
