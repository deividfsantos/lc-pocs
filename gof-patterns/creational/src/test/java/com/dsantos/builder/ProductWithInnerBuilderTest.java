package com.dsantos.builder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProductWithInnerBuilderTest {

    @Test
    void testBuilder() {
        ProductWithInnerBuilder product = ProductWithInnerBuilder.ProductWithInnerBuilderBuilder.aProduct()
                .withId(1)
                .withName("Product Name")
                .withBrand("Product Brand")
                .build();

        assertNotNull(product);
        assertEquals(1, product.getId());
        assertEquals("Product Name", product.getName());
        assertEquals("Product Brand", product.getBrand());
    }
}