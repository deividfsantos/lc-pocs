package com.dsantos;

public class Main {
    static void main() {
        RestaurantQueue queue = new RestaurantQueue();

        queue.enqueue(new Dish("Salad", 5));
        queue.enqueue(new Dish("Soup", 10));
        queue.enqueue(new Dish("Steak", 20));
        queue.enqueue(new Dish("Fries", 8));

        System.out.println("Current queue with estimates (startsIn / readyIn):");
        for (RestaurantQueue.DishEstimate est : queue.estimates()) {
            System.out.println(" - " + est);
        }

        System.out.println();
        System.out.println("Serving next dish: " + queue.dequeue());

        System.out.println();
        System.out.println("Updated queue with estimates:");
        for (RestaurantQueue.DishEstimate est : queue.estimates()) {
            System.out.println(" - " + est);
        }
    }
}
