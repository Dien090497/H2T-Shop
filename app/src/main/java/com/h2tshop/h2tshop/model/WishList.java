package com.h2tshop.h2tshop.model;

public class WishList {
    private String id;
    private String ten;
    private String link;
    private String type;
    private boolean favorite;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public WishList() {
    }

    public WishList(String id, String ten, String link, String type, boolean favorite, double gia) {
        this.id = id;
        this.ten = ten;
        this.link = link;
        this.type = type;
        this.favorite = favorite;
        this.gia = gia;
    }

}
