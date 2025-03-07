package com.dsantos.builder;

public class ProductWithInnerBuilder {
    
    private final Integer id;
    private final String name;
    private final String brand;

    public ProductWithInnerBuilder(Integer id, String name, String brand) {
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

    public static final class ProductWithInnerBuilderBuilder {
        private Integer id;
        private String name;
        private String brand;

        private ProductWithInnerBuilderBuilder() {
        }

        public static ProductWithInnerBuilderBuilder aProduct() {
            return new ProductWithInnerBuilderBuilder();
        }

        public ProductWithInnerBuilderBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public ProductWithInnerBuilderBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ProductWithInnerBuilderBuilder withBrand(String brand) {
            this.brand = brand;
            return this;
        }

        public ProductWithInnerBuilder build() {
            return new ProductWithInnerBuilder(id, name, brand);
        }
    }
}
