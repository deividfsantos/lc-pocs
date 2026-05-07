package com.dsantos.observability.metric;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Histogram {

    private final List<Long> values = new ArrayList<>();

    public synchronized void record(long valueNanos) {
        values.add(valueNanos);
    }

    public synchronized LatencySnapshot snapshot() {
        if (values.isEmpty()) return LatencySnapshot.empty();

        List<Long> sorted = new ArrayList<>(values);
        Collections.sort(sorted);

        long min = sorted.getFirst();
        long max = sorted.getLast();
        double mean = sorted.stream().mapToLong(Long::longValue).average().orElse(0);

        return new LatencySnapshot(
                sorted.size(), min, max, (long) mean,
                percentile(sorted, 50),
                percentile(sorted, 90),
                percentile(sorted, 95),
                percentile(sorted, 99)
        );
    }

    private long percentile(List<Long> sorted, int p) {
        int index = (int) Math.ceil(p / 100.0 * sorted.size()) - 1;
        return sorted.get(Math.max(0, Math.min(index, sorted.size() - 1)));
    }

    public synchronized void reset() {
        values.clear();
    }
}
