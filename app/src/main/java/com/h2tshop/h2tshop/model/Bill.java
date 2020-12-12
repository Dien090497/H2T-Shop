package com.h2tshop.h2tshop.model;

public class Bill {
    private String email,idBill;
    private double price;
    private boolean status;
    private String dateBill;

    public Bill() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdBill() {
        return idBill;
    }

    public void setIdBill(String idBill) {
        this.idBill = idBill;
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

    public String getDateBill() {
        return dateBill;
    }

    public void setDateBill(String dateBill) {
        this.dateBill = dateBill;
    }

    public Bill(String email, String idBill, double price, boolean status, String dateBill) {
        this.email = email;
        this.idBill = idBill;
        this.price = price;
        this.status = status;
        this.dateBill = dateBill;
    }
}
