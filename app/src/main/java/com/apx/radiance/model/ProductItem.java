package com.apx.radiance.model;

public class ProductItem {

    private int imageSource;
    private String name;
    private String brand;
    private String category;
    private Double price;

    public ProductItem() {
    }

    public ProductItem(int imageSource, String name, String brand, String category, Double price) {
        this.imageSource = imageSource;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.price = price;
    }

    public int getImageSource() {
        return imageSource;
    }

    public void setImageSource(int imageSource) {
        this.imageSource = imageSource;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
