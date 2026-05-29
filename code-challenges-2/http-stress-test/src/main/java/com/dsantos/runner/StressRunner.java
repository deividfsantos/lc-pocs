package com.dsantos.runner;
import com.dsantos.config.StressTestConfig;
import com.dsantos.stats.StatsCollector;
import com.dsantos.worker.RequestWorker;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
public class StressRunner {
    private final StressTestConfig config;
    private final StatsCollector stats;
    public StressRunner(StressTestConfig config, StatsCollector stats) {
        this.config = config;
        this.stats = stats;
    }
    public long runByCount() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(config.getConcurrency());
        long start = System.currentTimeMillis();
        for (int i = 0; i < config.getTotalRequests(); i++) {
            executor.submit(new RequestWorker(config, stats));
        }
        executor.shutdown();
        executor.awaitTermination(120, TimeUnit.SECONDS);
        return System.currentTimeMillis() - start;
    }
    public long runByDuration(int durationSeconds) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(config.getConcurrency());
        AtomicBoolean running = new AtomicBoolean(true);
        long start = System.currentTimeMillis();
        for (int i = 0; i < config.getConcurrency(); i++) {
            executor.submit(() -> {
                while (running.get()) {
                    new RequestWorker(config, stats).run();
                }
            });
        }
        Thread.sleep(durationSeconds * 1000L);
        running.set(false);
        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);
        return System.currentTimeMillis() - start;
    }
}
