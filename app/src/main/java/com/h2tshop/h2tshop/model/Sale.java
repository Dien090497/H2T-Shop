package com.h2tshop.h2tshop.model;

public class Sale {
    private String id;
    private String ten;
    private String type;
    private double sale;
    private double gia;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getSale() {
        return sale;
    }

    public void setSale(double sale) {
        this.sale = sale;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public Sale() {
    }

    public Sale(String id, String ten, String type, double sale, double gia) {
        this.id = id;
        this.ten = ten;
        this.type = type;
        this.sale = sale;
        this.gia = gia;
    }
}
