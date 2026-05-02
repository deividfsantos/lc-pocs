package com.dsantos.observability.metric;

import java.time.Instant;
import java.util.Map;

public record MetricPoint(String name, double value, String unit, Instant timestamp, Map<String, String> tags) {}
