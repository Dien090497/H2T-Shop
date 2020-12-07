package com.h2tshop.h2tshop.model;

public class Shoes {
    private double gia,sale;
    private String id,ten,mota;

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public double getSale() {
        return sale;
    }

    public void setSale(double sale) {
        this.sale = sale;
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

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public Shoes() {
    }

    public Shoes(double gia, double sale, String id, String ten, String mota) {
        this.gia = gia;
        this.sale = sale;
        this.id = id;
        this.ten = ten;
        this.mota = mota;
    }
}
