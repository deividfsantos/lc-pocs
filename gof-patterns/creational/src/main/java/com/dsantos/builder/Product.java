package com.dsantos.builder;

public class Product {

    private final Integer id;
    private final String name;
    private final String brand;

    public Product(Integer id, String name, String brand) {
        this.id = id;
        this.name = name;
        this.brand = brand;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }
    
    
}
