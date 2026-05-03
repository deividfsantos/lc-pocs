package com.dsantos.observability.collector;

import com.dsantos.observability.metric.LatencyMetric;

import java.time.Instant;

public final class Stopwatch {

    private final String name;
    private final String[] tags;
    private long startNanos;
    private boolean running;

    private Stopwatch(String name, String... tags) {
        this.name = name;
        this.tags = tags;
    }

    public static Stopwatch start(String name, String... tags) {
        Stopwatch sw = new Stopwatch(name, tags);
        sw.startNanos = System.nanoTime();
        sw.running = true;
        return sw;
    }

    public LatencyMetric stop() {
        if (!running) throw new IllegalStateException("Stopwatch is not running");
        long durationNanos = System.nanoTime() - startNanos;
        running = false;
        return new LatencyMetric(name, durationNanos, Instant.now(), tags);
    }

    public boolean isRunning() {
        return running;
    }

    public long elapsedNanos() {
        return running ? System.nanoTime() - startNanos : 0;
    }
}
