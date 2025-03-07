package com.dsantos.builder;

public final class ProductBuilderWithBut {
    private Integer id;
    private String name;
    private String brand;

    private ProductBuilderWithBut() {
    }

    public static ProductBuilderWithBut aProduct() {
        return new ProductBuilderWithBut();
    }

    public ProductBuilderWithBut withId(Integer id) {
        this.id = id;
        return this;
    }

    public ProductBuilderWithBut withName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilderWithBut withBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public ProductBuilderWithBut but() {
        return aProduct().withId(id).withName(name).withBrand(brand);
    }

    public Product build() {
        return new Product(id, name, brand);
    }
}
