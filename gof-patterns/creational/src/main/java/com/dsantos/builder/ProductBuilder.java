package com.dsantos.builder;

public final class ProductBuilder {
    private Integer id;
    private String name;
    private String brand;

    private ProductBuilder() {
    }

    public static ProductBuilder aProduct() {
        return new ProductBuilder();
    }

    public ProductBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public ProductBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder withBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public Product build() {
        return new Product(id, name, brand);
    }
}
