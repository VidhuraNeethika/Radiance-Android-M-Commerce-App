package com.apx.radiance.model;

public class Orders {

    private int id;

    private ProductItem product;

    private String date;

    private int qty;

    public Orders() {
    }

    public Orders(ProductItem product, String date, int qty) {
        this.product = product;
        this.date = date;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductItem getProduct() {
        return product;
    }

    public void setProduct(ProductItem product) {
        this.product = product;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

}
