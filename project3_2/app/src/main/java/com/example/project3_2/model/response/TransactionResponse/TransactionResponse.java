package com.example.project3_2.model.response.TransactionResponse;

import com.example.project3_2.model.Product;
import com.example.project3_2.model.response.BaseResponse;

import java.util.Date;

public class TransactionResponse extends BaseResponse {
    int id;
    String time;
    String customerName;
    String customerPhone;
    String userName;
    String status;
    Product product;
    String note;

    public TransactionResponse() {
    }

    public TransactionResponse(int id, String time, String customerName,
                               String customerPhone, String userName,
                               String status, Product product, String note) {
        this.id = id;
        this.time = time;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.userName = userName;
        this.status = status;
        this.product = product;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
