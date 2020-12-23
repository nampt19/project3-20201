package com.example.project3.domain.request;

import java.util.Date;

public class CreateTransactionRequest {
    int idCustomer;
    int idUser;
    int idProduct;
    Date time;
    String status;
    String note;

    public CreateTransactionRequest() {
    }

    public CreateTransactionRequest(int idCustomer, int idUser, int idProduct, Date time, String status, String note) {
        this.idCustomer = idCustomer;
        this.idUser = idUser;
        this.idProduct = idProduct;
        this.time = time;
        this.status = status;
        this.note = note;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
