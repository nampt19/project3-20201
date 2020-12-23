package com.example.project3_2.model;

import java.io.Serializable;
import java.util.Date;

public class UserModel implements Serializable {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String password;
    private String address;
    private String urlImage;
    private int roleId;
    private String token;
    private int createByIdUser;
    private String createTime;

    public UserModel() {
    }

    public UserModel(String name, String phone, String email, String role) {
        this.name = name;
        this.phone = phone;
        this.email = email;
//        this.role = role;
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

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    @Override
    public String toString() {
        return "UserModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", urlImage='" + urlImage + '\'' +
                ", roleId=" + roleId +
                ", token='" + token + '\'' +
                ", createByIdUser=" + createByIdUser +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
