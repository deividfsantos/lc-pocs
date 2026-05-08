package com.dsantos.observability.reporter;

import com.dsantos.observability.metric.Histogram;
import com.dsantos.observability.metric.LatencySnapshot;
import com.dsantos.observability.registry.MetricRegistry;

public class ConsoleReporter implements MetricReporter {

    @Override
    public void report(MetricRegistry registry) {
        System.out.println("=== Latency Report ===");
        registry.getAllMetrics().forEach((name, metrics) -> {
            if (metrics.isEmpty()) return;
            Histogram histogram = new Histogram();
            metrics.forEach(m -> histogram.record(m.durationNanos()));
            LatencySnapshot snap = histogram.snapshot();
            System.out.printf("[%s] count=%d  mean=%.2fms  p50=%.2fms  p90=%.2fms  p95=%.2fms  p99=%.2fms%n",
                    name, snap.count(), snap.meanMillis(), snap.p50Millis(),
                    snap.p90Millis(), snap.p95Millis(), snap.p99Millis());
        });
    }
}
