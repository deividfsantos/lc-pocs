//package com.dsantos.hibernate;
//
//import org.hibernate.resource.jdbc.spi.StatementInspector;
//
//public class SlowQueryInspector implements StatementInspector {
//    private static final long SLOW_QUERY_THRESHOLD = 1000; // 1 segundo
//
//    @Override
//    public String inspect(String sql) {
//        long startTime = System.currentTimeMillis();
//        SlowQueryContextHolder.setStartTime(startTime);
//        return sql;
//    }
//}
//
//class SlowQueryContextHolder {
//    private static final ThreadLocal<Long> startTimeHolder = new ThreadLocal<>();
//
//    public static void setStartTime(long startTime) {
//        startTimeHolder.set(startTime);
//    }
//
//    public static long getStartTime() {
//        return startTimeHolder.get();
//    }
//}