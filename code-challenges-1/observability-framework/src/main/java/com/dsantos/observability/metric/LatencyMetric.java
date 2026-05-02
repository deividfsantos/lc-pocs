package com.dsantos.observability.metric;

import java.time.Instant;

public record LatencyMetric(String name, long durationNanos, Instant timestamp, String[] tags) {

    public double durationMillis() {
        return durationNanos / 1_000_000.0;
    }

    public double durationSeconds() {
        return durationNanos / 1_000_000_000.0;
    }
}
