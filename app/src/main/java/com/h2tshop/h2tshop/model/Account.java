package com.h2tshop.h2tshop.model;

public class Account {
    private String email,ten,huyen,thanhPho,sdt,ngaySinh,link;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getHuyen() {
        return huyen;
    }

    public void setHuyen(String huyen) {
        this.huyen = huyen;
    }

    public String getThanhPho() {
        return thanhPho;
    }

    public void setThanhPho(String thanhPho) {
        this.thanhPho = thanhPho;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Account() {
    }

    public Account(String email, String ten, String huyen, String thanhPho, String sdt, String ngaySinh, String link) {
        this.email = email;
        this.ten = ten;
        this.huyen = huyen;
        this.thanhPho = thanhPho;
        this.sdt = sdt;
        this.ngaySinh = ngaySinh;
        this.link = link;
    }
}
