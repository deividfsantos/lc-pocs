package com.dsantos.observability.interceptor;

import com.dsantos.observability.annotation.Timed;
import com.dsantos.observability.collector.Stopwatch;
import com.dsantos.observability.metric.LatencyMetric;
import com.dsantos.observability.registry.MetricRegistry;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MethodTimer {

    private final MetricRegistry registry;

    public MethodTimer(MetricRegistry registry) {
        this.registry = registry;
    }

    @SuppressWarnings("unchecked")
    public <T> T wrap(T target, Class<T> iface) {
        return (T) Proxy.newProxyInstance(
                iface.getClassLoader(),
                new Class<?>[]{iface},
                new TimingHandler(target)
        );
    }

    private class TimingHandler implements InvocationHandler {
        private final Object target;

        TimingHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Timed timed = method.getAnnotation(Timed.class);
            Method targetMethod = resolveTargetMethod(method);

            if (timed == null) {
                return targetMethod.invoke(target, args);
            }

            String metricName = timed.value().isBlank() ? method.getName() : timed.value();
            Stopwatch sw = Stopwatch.start(metricName, timed.tags());
            try {
                return targetMethod.invoke(target, args);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            } finally {
                LatencyMetric metric = sw.stop();
                registry.record(metric);
            }
        }

        private Method resolveTargetMethod(Method method) throws NoSuchMethodException {
            Method m = target.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
            m.setAccessible(true);
            return m;
        }
    }
}
