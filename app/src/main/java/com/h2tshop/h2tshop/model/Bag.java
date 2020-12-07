package com.h2tshop.h2tshop.model;

public class Bag {
    private double gia,sale;
    private String id,ten,size,mota;

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public Bag() {
    }

    public double getSale() {
        return sale;
    }

    public void setSale(double sale) {
        this.sale = sale;
    }

    public Bag(double gia, double sale, String id, String ten, String size, String mota) {
        this.gia = gia;
        this.sale = sale;
        this.id = id;
        this.ten = ten;
        this.size = size;
        this.mota = mota;
    }
}
