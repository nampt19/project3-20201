package com.example.project3_2.model.response.TransactionResponse;

import java.util.Date;

public class TransactionRes {

    int id;
    String customerName;
    String time;
    int price;
    String status;

    public TransactionRes() {
    }

    public TransactionRes(int id, String customerName, String time, int price, String status) {
        this.id = id;
        this.customerName = customerName;
        this.time = time;
        this.price = price;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
