package com.apx.radiance.model;

import java.util.ArrayList;

public class Product {

    private ArrayList<String> imageList;
    private String name;
    private String brand;
    private String category;
    private Double price;

    public Product() {
    }

    public Product(ArrayList<String> imageList, String name, String brand, String category, Double price) {
        this.imageList = imageList;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.price = price;
    }

    public ArrayList<String> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
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
