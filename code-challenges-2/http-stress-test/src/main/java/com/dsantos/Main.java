package com.dsantos;
import com.dsantos.config.StressTestConfig;
import com.dsantos.report.ReportPrinter;
import com.dsantos.runner.StressRunner;
import com.dsantos.stats.StatsCollector;
public class Main {
    public static void main(String[] args) throws InterruptedException {
        StressTestConfig config = StressTestConfig.builder("https://httpbin.org/get")
                .method("GET")
                .concurrency(10)
                .totalRequests(50)
                .timeoutSeconds(10)
                .build();
        StatsCollector stats = new StatsCollector();
        StressRunner runner = new StressRunner(config, stats);
        long elapsed = runner.runByCount();
        new ReportPrinter().print(stats, elapsed);
    }
}
