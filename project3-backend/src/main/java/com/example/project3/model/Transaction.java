package com.example.project3.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_customer")
    private int idCustomer;

    @Column(name = "id_user")
    private int idUser;

    @Column(name = "id_product")
    private int idProduct;

    @Column(name = "time")
    private Date time;

    @Column(name = "status")
    private String status;

    @Column(name = "note")
    private String note;

    public Transaction() {
    }

    public Transaction(int id, int idCustomer, int idUser, int idProduct, Date time, String status, String note) {
        this.id = id;
        this.idCustomer = idCustomer;
        this.idUser = idUser;
        this.idProduct = idProduct;
        this.time = time;
        this.status = status;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
