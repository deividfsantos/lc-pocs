package com.dsantos;

import java.util.*;

public class RestaurantQueue {
    private final Queue<Dish> queue = new LinkedList<>();

    public RestaurantQueue() {
    }

    public synchronized void enqueue(Dish dish) {
        Objects.requireNonNull(dish, "dish required");
        queue.add(dish);
    }

    public synchronized Dish dequeue() {
        return queue.poll();
    }

    public synchronized int size() {
        return queue.size();
    }

    public synchronized List<DishEstimate> estimates() {
        if (queue.isEmpty()) return Collections.emptyList();
        List<DishEstimate> out = new ArrayList<>(queue.size());
        int accumulated = 0; // minutes until the current dish starts
        for (Dish d : queue) {
            int startsIn = accumulated;
            int readyIn = accumulated + d.prepMinutes();
            out.add(new DishEstimate(d, startsIn, readyIn));
            accumulated = readyIn; // next dish starts when this one is ready
        }
        return Collections.unmodifiableList(out);
    }

    public record DishEstimate(Dish dish, int startsInMinutes, int readyInMinutes) {
        public DishEstimate(Dish dish, int startsInMinutes, int readyInMinutes) {
            this.dish = Objects.requireNonNull(dish);
            if (startsInMinutes < 0 || readyInMinutes < 0) throw new IllegalArgumentException("times must be >= 0");
            if (readyInMinutes < startsInMinutes) throw new IllegalArgumentException("readyIn must be >= startsIn");
            this.startsInMinutes = startsInMinutes;
            this.readyInMinutes = readyInMinutes;
        }
    }
}

