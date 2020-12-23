package com.example.project3_2.model;

import java.io.Serializable;

public class CustomerModel implements Serializable {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String status;
    private String address;
    private int createByIdUser;
    private String createTime;

    public CustomerModel() {
    }

    public CustomerModel(String name, String phone, String email, String status) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCreateByIdUser() {
        return createByIdUser;
    }

    public void setCreateByIdUser(int createByIdUser) {
        this.createByIdUser = createByIdUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
