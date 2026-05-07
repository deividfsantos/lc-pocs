package com.dsantos.observability.metric;

public record LatencySnapshot(
        int count,
        long minNanos,
        long maxNanos,
        long meanNanos,
        long p50Nanos,
        long p90Nanos,
        long p95Nanos,
        long p99Nanos
) {

    public static LatencySnapshot empty() {
        return new LatencySnapshot(0, 0, 0, 0, 0, 0, 0, 0);
    }

    public double minMillis() { return minNanos / 1_000_000.0; }
    public double maxMillis() { return maxNanos / 1_000_000.0; }
    public double meanMillis() { return meanNanos / 1_000_000.0; }
    public double p50Millis() { return p50Nanos / 1_000_000.0; }
    public double p90Millis() { return p90Nanos / 1_000_000.0; }
    public double p95Millis() { return p95Nanos / 1_000_000.0; }
    public double p99Millis() { return p99Nanos / 1_000_000.0; }

    @Override
    public String toString() {
        return String.format(
                "LatencySnapshot{count=%d, min=%.2fms, max=%.2fms, mean=%.2fms, p50=%.2fms, p90=%.2fms, p95=%.2fms, p99=%.2fms}",
                count, minMillis(), maxMillis(), meanMillis(), p50Millis(), p90Millis(), p95Millis(), p99Millis()
        );
    }
}
