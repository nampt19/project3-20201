package com.example.project3.domain.response;

import com.example.project3.model.Product;

import java.util.Date;

public class TransactionResponse extends BaseResponse {
    int id;
    Date time;
    String customerName;
    String customerPhone;
    String userName;
    String status;
    Product product;
    String note;

    public TransactionResponse() {
    }

    public TransactionResponse(int id, Date time, String customerName,
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
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
