package com.dsantos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

/**
 * Simple restaurant queue that holds Dish objects and can provide time estimates
 * for when each dish will start and be ready (in minutes from now).
 */
public class RestaurantQueue {
    private final Queue<Dish> queue = new LinkedList<>();

    public RestaurantQueue() {
    }

    public synchronized void enqueue(Dish dish) {
        Objects.requireNonNull(dish, "dish required");
        queue.add(dish);
    }

    /**
     * Removes and returns the head of the queue, or null if empty.
     */
    public synchronized Dish dequeue() {
        return queue.poll();
    }

    public synchronized int size() {
        return queue.size();
    }

    /**
     * Returns an immutable list of estimates for each dish currently in the queue.
     * Each estimate contains: the dish, minutes until it starts, and minutes until it's ready.
     */
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

    public static final class DishEstimate {
        private final Dish dish;
        private final int startsInMinutes;
        private final int readyInMinutes;

        public DishEstimate(Dish dish, int startsInMinutes, int readyInMinutes) {
            this.dish = Objects.requireNonNull(dish);
            if (startsInMinutes < 0 || readyInMinutes < 0) throw new IllegalArgumentException("times must be >= 0");
            if (readyInMinutes < startsInMinutes) throw new IllegalArgumentException("readyIn must be >= startsIn");
            this.startsInMinutes = startsInMinutes;
            this.readyInMinutes = readyInMinutes;
        }

        public Dish dish() { return dish; }
        public int startsInMinutes() { return startsInMinutes; }
        public int readyInMinutes() { return readyInMinutes; }

        @Override
        public String toString() {
            return dish.toString() + " (starts in " + startsInMinutes + "m, ready in " + readyInMinutes + "m)";
        }
    }
}

