package com.dsantos.singleton;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class SingletonInstanceHolderTest {

    @Test
    public void testSingletonInstanceHolderInstance() {
        SingletonInstanceHolder instance1 = SingletonInstanceHolder.getInstance();
        SingletonInstanceHolder instance2 = SingletonInstanceHolder.getInstance();

        assertNotNull(instance1);
        assertNotNull(instance2);
        assertSame(instance1, instance2, "Both instances should be the same");
    }
}