package com.dsantos;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantQueueTest {

    @Test
    void estimatesBeforeAndAfterDequeue() {
        RestaurantQueue q = new RestaurantQueue();
        q.enqueue(new Dish("A", 5));
        q.enqueue(new Dish("B", 10));
        q.enqueue(new Dish("C", 20));

        List<RestaurantQueue.DishEstimate> estimates = q.estimates();
        assertEquals(3, estimates.size());

        assertEquals("A", estimates.get(0).dish().name());
        assertEquals(0, estimates.get(0).startsInMinutes());
        assertEquals(5, estimates.get(0).readyInMinutes());

        assertEquals("B", estimates.get(1).dish().name());
        assertEquals(5, estimates.get(1).startsInMinutes());
        assertEquals(15, estimates.get(1).readyInMinutes());

        assertEquals("C", estimates.get(2).dish().name());
        assertEquals(15, estimates.get(2).startsInMinutes());
        assertEquals(35, estimates.get(2).readyInMinutes());

        // dequeue first
        Dish served = q.dequeue();
        assertNotNull(served);
        assertEquals("A", served.name());

        List<RestaurantQueue.DishEstimate> after = q.estimates();
        assertEquals(2, after.size());

        assertEquals("B", after.get(0).dish().name());
        assertEquals(0, after.get(0).startsInMinutes());
        assertEquals(10, after.get(0).readyInMinutes());

        assertEquals("C", after.get(1).dish().name());
        assertEquals(10, after.get(1).startsInMinutes());
        assertEquals(30, after.get(1).readyInMinutes());
    }

    @Test
    void emptyQueueEstimates() {
        RestaurantQueue q = new RestaurantQueue();
        List<RestaurantQueue.DishEstimate> est = q.estimates();
        assertNotNull(est);
        assertTrue(est.isEmpty());
        assertNull(q.dequeue());
    }
}

