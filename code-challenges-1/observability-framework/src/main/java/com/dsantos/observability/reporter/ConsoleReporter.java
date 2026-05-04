package com.dsantos.observability.reporter;

import com.dsantos.observability.metric.LatencyMetric;
import com.dsantos.observability.registry.MetricRegistry;

public class ConsoleReporter implements MetricReporter {

    @Override
    public void report(MetricRegistry registry) {
        System.out.println("=== Latency Report ===");
        registry.getAllMetrics().forEach((name, metrics) -> {
            if (metrics.isEmpty()) return;
            double avg = metrics.stream().mapToLong(LatencyMetric::durationNanos)
                    .average().orElse(0) / 1_000_000.0;
            long min = metrics.stream().mapToLong(LatencyMetric::durationNanos).min().orElse(0);
            long max = metrics.stream().mapToLong(LatencyMetric::durationNanos).max().orElse(0);
            System.out.printf("[%s] count=%d  avg=%.2fms  min=%.2fms  max=%.2fms%n",
                    name, metrics.size(), avg, min / 1_000_000.0, max / 1_000_000.0);
        });
    }
}
