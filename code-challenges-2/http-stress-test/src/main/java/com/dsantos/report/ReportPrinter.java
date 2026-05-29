package com.dsantos.report;
import com.dsantos.stats.ResponseRecord;
import com.dsantos.stats.StatsCollector;
import java.util.Map;
import java.util.stream.Collectors;
public class ReportPrinter {
    public void print(StatsCollector stats, long totalElapsedMillis) {
        System.out.println();
        System.out.println("=== Stress Test Report ===");
        System.out.println("Total requests : " + stats.getRecords().size());
        System.out.println("Successful     : " + stats.getSuccessCount());
        System.out.println("Failed         : " + stats.getFailureCount());
        System.out.println("Min latency    : " + stats.getMinDurationMillis() + " ms");
        System.out.println("Max latency    : " + stats.getMaxDurationMillis() + " ms");
        System.out.printf("Avg latency    : %.2f ms%n", stats.getAverageDurationMillis());
        System.out.println("Total time     : " + totalElapsedMillis + " ms");
        System.out.println();
        System.out.println("Status codes:");
        Map<Integer, Long> statusCounts = stats.getRecords().stream()
                .collect(Collectors.groupingBy(ResponseRecord::getStatusCode, Collectors.counting()));
        statusCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.println("  " + e.getKey() + " -> " + e.getValue()));
        System.out.println("==========================");
    }
}
