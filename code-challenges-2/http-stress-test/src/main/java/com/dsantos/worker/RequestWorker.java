package com.dsantos.worker;
import com.dsantos.client.HttpRequestSender;
import com.dsantos.config.StressTestConfig;
import com.dsantos.stats.ResponseRecord;
import com.dsantos.stats.StatsCollector;
import java.net.http.HttpResponse;
public class RequestWorker implements Runnable {
    private final StressTestConfig config;
    private final HttpRequestSender sender;
    private final StatsCollector stats;
    public RequestWorker(StressTestConfig config, StatsCollector stats) {
        this.config = config;
        this.stats = stats;
        this.sender = new HttpRequestSender(config.getTimeoutSeconds());
    }
    @Override
    public void run() {
        long start = System.currentTimeMillis();
        try {
            HttpResponse<String> response = sender.send(config.getUrl(), config.getMethod());
            long duration = System.currentTimeMillis() - start;
            boolean success = response.statusCode() >= 200 && response.statusCode() < 300;
            stats.record(new ResponseRecord(response.statusCode(), duration, success));
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - start;
            stats.record(new ResponseRecord(0, duration, false));
        }
    }
}
