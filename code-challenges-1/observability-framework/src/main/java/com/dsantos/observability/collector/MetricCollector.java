package com.dsantos.observability.collector;

import com.dsantos.observability.metric.LatencyMetric;

public interface MetricCollector {
    void record(LatencyMetric metric);
}
