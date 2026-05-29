package com.dsantos;
import com.dsantos.config.StressTestConfig;
import com.dsantos.report.ReportPrinter;
import com.dsantos.stats.StatsCollector;
import com.dsantos.worker.RequestWorker;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
public class Main {
    public static void main(String[] args) throws InterruptedException {
        StressTestConfig config = StressTestConfig.builder("https://httpbin.org/get")
                .method("GET")
                .concurrency(10)
                .totalRequests(50)
                .timeoutSeconds(10)
                .build();
        StatsCollector stats = new StatsCollector();
        ExecutorService executor = Executors.newFixedThreadPool(config.getConcurrency());
        long start = System.currentTimeMillis();
        for (int i = 0; i < config.getTotalRequests(); i++) {
            executor.submit(new RequestWorker(config, stats));
        }
        executor.shutdown();
        executor.awaitTermination(120, TimeUnit.SECONDS);
        long elapsed = System.currentTimeMillis() - start;
        new ReportPrinter().print(stats, elapsed);
    }
}
