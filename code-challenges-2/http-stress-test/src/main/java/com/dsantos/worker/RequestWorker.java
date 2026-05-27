package com.dsantos.worker;
import com.dsantos.client.HttpRequestSender;
import com.dsantos.config.StressTestConfig;
import java.net.http.HttpResponse;
public class RequestWorker implements Runnable {
    private final StressTestConfig config;
    private final HttpRequestSender sender;
    public RequestWorker(StressTestConfig config) {
        this.config = config;
        this.sender = new HttpRequestSender(config.getTimeoutSeconds());
    }
    @Override
    public void run() {
        try {
            HttpResponse<String> response = sender.send(config.getUrl(), config.getMethod());
            System.out.println("[" + Thread.currentThread().getName() + "] status=" + response.statusCode());
        } catch (Exception e) {
            System.err.println("[" + Thread.currentThread().getName() + "] error: " + e.getMessage());
        }
    }
}
