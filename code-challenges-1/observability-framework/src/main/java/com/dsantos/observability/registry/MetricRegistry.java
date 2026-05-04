package com.dsantos.observability.registry;

import com.dsantos.observability.collector.MetricCollector;
import com.dsantos.observability.metric.LatencyMetric;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class MetricRegistry implements MetricCollector {

    private final Map<String, List<LatencyMetric>> metrics = new ConcurrentHashMap<>();

    @Override
    public void record(LatencyMetric metric) {
        metrics.computeIfAbsent(metric.name(), k -> new CopyOnWriteArrayList<>()).add(metric);
    }

    public List<LatencyMetric> getMetrics(String name) {
        return Collections.unmodifiableList(metrics.getOrDefault(name, List.of()));
    }

    public Map<String, List<LatencyMetric>> getAllMetrics() {
        return Collections.unmodifiableMap(metrics);
    }

    public void clear() {
        metrics.clear();
    }
}
