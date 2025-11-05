package com.dsantos;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantQueueTest {

    @Test
    void estimatesForThreeDishes() {
        RestaurantQueue q = new RestaurantQueue();
        q.enqueue(Dishes.SALAD);
        q.enqueue(Dishes.SOUP);
        q.enqueue(Dishes.STEAK);

        List<RestaurantQueue.DishEstimate> estimates = q.estimates();
        assertEquals(3, estimates.size());

        assertEquals(Dishes.SALAD, estimates.get(0).dish());
        assertEquals(0, estimates.get(0).startsInMinutes());
        assertEquals(5, estimates.get(0).readyInMinutes());

        assertEquals(Dishes.SOUP, estimates.get(1).dish());
        assertEquals(5, estimates.get(1).startsInMinutes());
        assertEquals(15, estimates.get(1).readyInMinutes());

        assertEquals(Dishes.STEAK, estimates.get(2).dish());
        assertEquals(15, estimates.get(2).startsInMinutes());
        assertEquals(35, estimates.get(2).readyInMinutes());
    }

    @Test
    void dequeueRemovesFirstAndShiftsEstimates() {
        RestaurantQueue q = new RestaurantQueue();
        q.enqueue(Dishes.SALAD);
        q.enqueue(Dishes.SOUP);
        q.enqueue(Dishes.STEAK);

        Dishes served = q.dequeue();
        assertNotNull(served);
        assertEquals(Dishes.SALAD, served);

        List<RestaurantQueue.DishEstimate> after = q.estimates();
        assertEquals(2, after.size());

        assertEquals(Dishes.SOUP, after.get(0).dish());
        assertEquals(0, after.get(0).startsInMinutes());
        assertEquals(10, after.get(0).readyInMinutes());

        assertEquals(Dishes.STEAK, after.get(1).dish());
        assertEquals(10, after.get(1).startsInMinutes());
        assertEquals(30, after.get(1).readyInMinutes());
    }

    @Test
    void emptyQueueEstimatesAndDequeue() {
        RestaurantQueue q = new RestaurantQueue();
        List<RestaurantQueue.DishEstimate> est = q.estimates();
        assertNotNull(est);
        assertTrue(est.isEmpty());
        assertNull(q.dequeue());
    }

    @Test
    void enqueueNullThrows() {
        RestaurantQueue q = new RestaurantQueue();
        assertThrows(NullPointerException.class, () -> q.enqueue(null));
    }

    @Test
    void queueOrderAndSize() {
        RestaurantQueue q = new RestaurantQueue();
        q.enqueue(Dishes.SALAD);
        q.enqueue(Dishes.FRIES);
        q.enqueue(Dishes.SOUP);

        assertEquals(3, q.size());
        assertEquals(Dishes.SALAD, q.dequeue());
        assertEquals(2, q.size());
        assertEquals(Dishes.FRIES, q.dequeue());
        assertEquals(Dishes.SOUP, q.dequeue());
        assertEquals(0, q.size());
    }

    @Test
    void dishEstimateValidation() {
        // negative start
        assertThrows(IllegalArgumentException.class, () -> new RestaurantQueue.DishEstimate(Dishes.SALAD, -1, 0));
        // ready before start
        assertThrows(IllegalArgumentException.class, () -> new RestaurantQueue.DishEstimate(Dishes.SALAD, 5, 4));
        // null dish
        assertThrows(NullPointerException.class, () -> new RestaurantQueue.DishEstimate(null, 0, 0));
    }
}
