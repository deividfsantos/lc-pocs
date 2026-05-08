package com.dsantos.observability.reporter;

import com.dsantos.observability.metric.Histogram;
import com.dsantos.observability.metric.LatencySnapshot;
import com.dsantos.observability.registry.MetricRegistry;

import java.util.Locale;
import java.util.StringJoiner;

public class JsonReporter implements MetricReporter {

    @Override
    public void report(MetricRegistry registry) {
        StringJoiner entries = new StringJoiner(",\n    ", "{\n  \"metrics\": [\n    ", "\n  ]\n}");

        registry.getAllMetrics().forEach((name, metrics) -> {
            if (metrics.isEmpty()) return;
            Histogram histogram = new Histogram();
            metrics.forEach(m -> histogram.record(m.durationNanos()));
            LatencySnapshot snapshot = histogram.snapshot();
            entries.add(toJson(name, snapshot));
        });

        System.out.println(entries);
    }

    private String toJson(String name, LatencySnapshot snap) {
        return String.format(Locale.US,
                """
                {
              "name": "%s",
              "count": %d,
              "min_ms": %.3f,
              "max_ms": %.3f,
              "mean_ms": %.3f,
              "p50_ms": %.3f,
              "p90_ms": %.3f,
              "p95_ms": %.3f,
              "p99_ms": %.3f
            }""",
                name, snap.count(), snap.minMillis(), snap.maxMillis(),
                snap.meanMillis(), snap.p50Millis(), snap.p90Millis(),
                snap.p95Millis(), snap.p99Millis());
    }
}
