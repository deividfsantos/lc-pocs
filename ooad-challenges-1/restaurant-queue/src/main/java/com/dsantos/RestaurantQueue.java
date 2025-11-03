package com.dsantos;

import java.util.*;

public class RestaurantQueue {
    private final Queue<Dishes> queue = new LinkedList<>();

    public RestaurantQueue() {
    }

    public synchronized void enqueue(Dishes dish) {
        Objects.requireNonNull(dish, "dish required");
        queue.add(dish);
    }

    public synchronized Dishes dequeue() {
        return queue.poll();
    }

    public synchronized int size() {
        return queue.size();
    }

    public synchronized List<DishEstimate> estimates() {
        if (queue.isEmpty()) return Collections.emptyList();
        List<DishEstimate> out = new ArrayList<>(queue.size());
        int accumulated = 0; // minutes until the current dish starts
        for (Dishes d : queue) {
            int startsIn = accumulated;
            int readyIn = accumulated + d.prepMinutes();
            out.add(new DishEstimate(d, startsIn, readyIn));
            accumulated = readyIn; // next dish starts when this one is ready
        }
        return Collections.unmodifiableList(out);
    }

    public record DishEstimate(Dishes dish, int startsInMinutes, int readyInMinutes) {
        public DishEstimate(Dishes dish, int startsInMinutes, int readyInMinutes) {
            this.dish = Objects.requireNonNull(dish);
            if (startsInMinutes < 0 || readyInMinutes < 0) throw new IllegalArgumentException("times must be >= 0");
            if (readyInMinutes < startsInMinutes) throw new IllegalArgumentException("readyIn must be >= startsIn");
            this.startsInMinutes = startsInMinutes;
            this.readyInMinutes = readyInMinutes;
        }
    }
}

