package com.dsantos.builder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProductWithInnerBuilderSingleFieldTest {

    @Test
    void testBuilder() {
        ProductWithInnerBuilderSingleField product = ProductWithInnerBuilderSingleField.ProductWithInnerBuilderSingleFieldBuilder.aProduct()
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