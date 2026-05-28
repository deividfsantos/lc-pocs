package com.dsantos.stats;
public class ResponseRecord {
    private final int statusCode;
    private final long durationMillis;
    private final boolean success;
    public ResponseRecord(int statusCode, long durationMillis, boolean success) {
        this.statusCode = statusCode;
        this.durationMillis = durationMillis;
        this.success = success;
    }
    public int getStatusCode() { return statusCode; }
    public long getDurationMillis() { return durationMillis; }
    public boolean isSuccess() { return success; }
}
