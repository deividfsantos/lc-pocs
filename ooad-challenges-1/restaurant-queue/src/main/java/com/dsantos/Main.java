package com.dsantos;

public class Main {
    static void main() {
        RestaurantQueue queue = new RestaurantQueue();

        queue.enqueue(Dish.SALAD);
        queue.enqueue(Dish.SOUP);
        queue.enqueue(Dish.SOUP);
        queue.enqueue(Dish.STEAK);
        queue.enqueue(Dish.FRIES);

        System.out.println("Current queue with estimates (startsIn / readyIn):");
        for (DishEstimate est : queue.estimates()) {
            System.out.println(" - " + est);
        }

        System.out.println();
        System.out.println("Serving next dish: " + queue.dequeue());

        System.out.println();
        System.out.println("Updated queue with estimates:");
        for (DishEstimate est : queue.estimates()) {
            System.out.println(" - " + est);
        }
    }
}
