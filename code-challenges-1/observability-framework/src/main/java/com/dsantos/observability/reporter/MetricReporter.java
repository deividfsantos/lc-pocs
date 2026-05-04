package com.dsantos.observability.reporter;

import com.dsantos.observability.registry.MetricRegistry;

public interface MetricReporter {
    void report(MetricRegistry registry);
}
