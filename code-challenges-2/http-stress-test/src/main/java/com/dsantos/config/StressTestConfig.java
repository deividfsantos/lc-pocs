package com.dsantos.config;
public class StressTestConfig {
    private final String url;
    private final String method;
    private final int concurrency;
    private final int totalRequests;
    private final int timeoutSeconds;
    public StressTestConfig(String url, String method, int concurrency, int totalRequests, int timeoutSeconds) {
        this.url = url;
        this.method = method;
        this.concurrency = concurrency;
        this.totalRequests = totalRequests;
        this.timeoutSeconds = timeoutSeconds;
    }
    public String getUrl() { return url; }
    public String getMethod() { return method; }
    public int getConcurrency() { return concurrency; }
    public int getTotalRequests() { return totalRequests; }
    public int getTimeoutSeconds() { return timeoutSeconds; }
}
