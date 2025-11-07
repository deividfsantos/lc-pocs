package com.dsantos;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantQueueTest {

    @Test
    void estimatesForThreeDishes() {
        RestaurantQueue q = new RestaurantQueue();
        q.enqueue(Dish.SALAD);
        q.enqueue(Dish.SOUP);
        q.enqueue(Dish.STEAK);

        List<DishEstimate> estimates = q.estimates();
        assertEquals(3, estimates.size());

        assertEquals(Dish.SALAD, estimates.getFirst().dish());
        assertEquals(0, estimates.get(0).startsInMinutes());
        assertEquals(5, estimates.get(0).readyInMinutes());

        assertEquals(Dish.SOUP, estimates.get(1).dish());
        assertEquals(5, estimates.get(1).startsInMinutes());
        assertEquals(15, estimates.get(1).readyInMinutes());

        assertEquals(Dish.STEAK, estimates.get(2).dish());
        assertEquals(15, estimates.get(2).startsInMinutes());
        assertEquals(35, estimates.get(2).readyInMinutes());
    }

    @Test
    void dequeueRemovesFirstAndShiftsEstimates() {
        RestaurantQueue q = new RestaurantQueue();
        q.enqueue(Dish.SALAD);
        q.enqueue(Dish.SOUP);
        q.enqueue(Dish.STEAK);

        Dish served = q.dequeue();
        assertNotNull(served);
        assertEquals(Dish.SALAD, served);

        List<DishEstimate> after = q.estimates();
        assertEquals(2, after.size());

        assertEquals(Dish.SOUP, after.getFirst().dish());
        assertEquals(0, after.get(0).startsInMinutes());
        assertEquals(10, after.get(0).readyInMinutes());

        assertEquals(Dish.STEAK, after.get(1).dish());
        assertEquals(10, after.get(1).startsInMinutes());
        assertEquals(30, after.get(1).readyInMinutes());
    }

    @Test
    void emptyQueueEstimatesAndDequeue() {
        RestaurantQueue q = new RestaurantQueue();
        List<DishEstimate> est = q.estimates();
        assertNotNull(est);
        assertTrue(est.isEmpty());
        assertNull(q.dequeue());
    }

    @Test
    void queueOrderAndSize() {
        RestaurantQueue q = new RestaurantQueue();
        q.enqueue(Dish.SALAD);
        q.enqueue(Dish.FRIES);
        q.enqueue(Dish.SOUP);

        assertEquals(3, q.size());
        assertEquals(Dish.SALAD, q.dequeue());
        assertEquals(2, q.size());
        assertEquals(Dish.FRIES, q.dequeue());
        assertEquals(Dish.SOUP, q.dequeue());
        assertEquals(0, q.size());
    }

    @Test
    void dishEstimateValidation() {
        // negative start
        assertThrows(IllegalArgumentException.class, () -> new DishEstimate(Dish.SALAD, -1, 0));
        // ready before start
        assertThrows(IllegalArgumentException.class, () -> new DishEstimate(Dish.SALAD, 5, 4));
        // null dish
        assertThrows(NullPointerException.class, () -> new DishEstimate(null, 0, 0));
    }
}
