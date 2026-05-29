package com.dsantos.config;
public class StressTestConfig {
    private final String url;
    private final String method;
    private final int concurrency;
    private final int totalRequests;
    private final int timeoutSeconds;
    private StressTestConfig(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.concurrency = builder.concurrency;
        this.totalRequests = builder.totalRequests;
        this.timeoutSeconds = builder.timeoutSeconds;
    }
    public String getUrl() { return url; }
    public String getMethod() { return method; }
    public int getConcurrency() { return concurrency; }
    public int getTotalRequests() { return totalRequests; }
    public int getTimeoutSeconds() { return timeoutSeconds; }
    public static Builder builder(String url) {
        return new Builder(url);
    }
    public static class Builder {
        private final String url;
        private String method = "GET";
        private int concurrency = 1;
        private int totalRequests = 10;
        private int timeoutSeconds = 10;
        private Builder(String url) {
            this.url = url;
        }
        public Builder method(String method) {
            this.method = method;
            return this;
        }
        public Builder concurrency(int concurrency) {
            this.concurrency = concurrency;
            return this;
        }
        public Builder totalRequests(int totalRequests) {
            this.totalRequests = totalRequests;
            return this;
        }
        public Builder timeoutSeconds(int timeoutSeconds) {
            this.timeoutSeconds = timeoutSeconds;
            return this;
        }
        public StressTestConfig build() {
            return new StressTestConfig(this);
        }
    }
}
