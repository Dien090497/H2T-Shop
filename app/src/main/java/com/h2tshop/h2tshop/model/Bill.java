package com.h2tshop.h2tshop.model;

public class Bill {
    public Bill() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Bill(String email, String product, double price, boolean status) {
        this.email = email;
        this.product = product;
        this.price = price;
        this.status = status;
    }

    private String email,product;
    private double price;
    private boolean status;
}
