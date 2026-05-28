package com.dsantos.stats;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
public class StatsCollector {
    private final List<ResponseRecord> records = new CopyOnWriteArrayList<>();
    public void record(ResponseRecord record) {
        records.add(record);
    }
    public List<ResponseRecord> getRecords() {
        return records;
    }
    public long getSuccessCount() {
        return records.stream().filter(ResponseRecord::isSuccess).count();
    }
    public long getFailureCount() {
        return records.stream().filter(r -> !r.isSuccess()).count();
    }
    public double getAverageDurationMillis() {
        return records.stream().mapToLong(ResponseRecord::getDurationMillis).average().orElse(0);
    }
    public long getMaxDurationMillis() {
        return records.stream().mapToLong(ResponseRecord::getDurationMillis).max().orElse(0);
    }
    public long getMinDurationMillis() {
        return records.stream().mapToLong(ResponseRecord::getDurationMillis).min().orElse(0);
    }
}
