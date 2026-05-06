package com.dsantos.observability;

import com.dsantos.observability.annotation.Timed;
import com.dsantos.observability.interceptor.MethodTimer;
import com.dsantos.observability.registry.MetricRegistry;
import com.dsantos.observability.reporter.ConsoleReporter;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        MetricRegistry registry = new MetricRegistry();
        MethodTimer timer = new MethodTimer(registry);

        Service service = timer.wrap(new ServiceImpl(), Service.class);

        for (int i = 0; i < 5; i++) {
            service.processRequest();
            service.fetchData();
        }

        new ConsoleReporter().report(registry);
    }

    interface Service {
        @Timed("process_request")
        void processRequest() throws InterruptedException;

        @Timed("fetch_data")
        void fetchData() throws InterruptedException;
    }

    static class ServiceImpl implements Service {
        @Override
        public void processRequest() throws InterruptedException {
            Thread.sleep((long) (Math.random() * 100 + 10));
        }

        @Override
        public void fetchData() throws InterruptedException {
            Thread.sleep((long) (Math.random() * 50 + 5));
        }
    }
}
