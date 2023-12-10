package com.apx.radiance.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Product {

    private ArrayList<String> imageList;
    private String name, description, category, brand, model,regDate,sellerEmail;
    private Double price;
    private int quantity;

    public Product() {
    }

    public Product(ArrayList<String> imageList, String name, String description, String category, String brand, String model, String regDate, String sellerEmail, Double price, int quantity) {
        this.imageList = imageList;
        this.name = name;
        this.description = description;
        this.category = category;
        this.brand = brand;
        this.model = model;
        this.regDate = regDate;
        this.sellerEmail = sellerEmail;
        this.price = price;
        this.quantity = quantity;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }
}
