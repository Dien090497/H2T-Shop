package com.h2tshop.h2tshop.model;

public class Cart {
    private String id,ten,size,type,link,iCheck;
    private int qnt;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getQnt() {
        return qnt;
    }

    public void setQnt(int qnt) {
        this.qnt = qnt;
    }

    public Cart() {
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getICheck() {
        return iCheck;
    }

    public void setICheck(String iCheck) {
        this.iCheck = iCheck;
    }

    public Cart(String id, String ten, String size, String type, String link, String iCheck, int qnt, double gia) {
        this.id = id;
        this.ten = ten;
        this.size = size;
        this.type = type;
        this.link = link;
        this.iCheck = iCheck;
        this.qnt = qnt;
        this.gia = gia;
    }
}
