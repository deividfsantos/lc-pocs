package com.dsantos.observability;

import com.dsantos.observability.collector.Stopwatch;
import com.dsantos.observability.metric.LatencyMetric;
import com.dsantos.observability.registry.MetricRegistry;
import com.dsantos.observability.reporter.ConsoleReporter;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        MetricRegistry registry = new MetricRegistry();

        for (int i = 0; i < 5; i++) {
            Stopwatch sw = Stopwatch.start("database.query");
            Thread.sleep((long) (Math.random() * 100 + 10));
            LatencyMetric metric = sw.stop();
            registry.record(metric);

            Stopwatch sw2 = Stopwatch.start("cache.lookup");
            Thread.sleep((long) (Math.random() * 20 + 2));
            registry.record(sw2.stop());
        }

        new ConsoleReporter().report(registry);
    }
}
