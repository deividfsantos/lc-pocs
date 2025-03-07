package com.dsantos.builder;

public class ProductWithInnerBuilderSingleField {

    private Integer id;
    private String name;
    private String brand;

    public ProductWithInnerBuilderSingleField() {
    }

    public ProductWithInnerBuilderSingleField(Integer id, String name, String brand) {
        this.id = id;
        this.name = name;
        this.brand = brand;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }


    public static final class ProductWithInnerBuilderSingleFieldBuilder {
        private final ProductWithInnerBuilderSingleField productWithInnerBuilderSingleField;

        private ProductWithInnerBuilderSingleFieldBuilder() {
            productWithInnerBuilderSingleField = new ProductWithInnerBuilderSingleField();
        }

        public static ProductWithInnerBuilderSingleFieldBuilder aProduct() {
            return new ProductWithInnerBuilderSingleFieldBuilder();
        }

        public ProductWithInnerBuilderSingleFieldBuilder withId(Integer id) {
            productWithInnerBuilderSingleField.setId(id);
            return this;
        }

        public ProductWithInnerBuilderSingleFieldBuilder withName(String name) {
            productWithInnerBuilderSingleField.setName(name);
            return this;
        }

        public ProductWithInnerBuilderSingleFieldBuilder withBrand(String brand) {
            productWithInnerBuilderSingleField.setBrand(brand);
            return this;
        }

        public ProductWithInnerBuilderSingleField build() {
            return productWithInnerBuilderSingleField;
        }
    }
}
