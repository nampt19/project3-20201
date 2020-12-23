package com.example.project3_2.model;

import java.io.Serializable;
import java.util.Date;

public class TransactionModel implements Serializable {
    int id;
    String customerName;
    String transactionTime;
    int priceCourse;
    String status;

    public TransactionModel() {
    }

    public TransactionModel(int id, String customerName, String transactionTime, int priceCourse, String status) {
        this.id = id;
        this.customerName = customerName;
        this.transactionTime = transactionTime;
        this.priceCourse = priceCourse;
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

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public int getPriceCourse() {
        return priceCourse;
    }

    public void setPriceCourse(int priceCourse) {
        this.priceCourse = priceCourse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
