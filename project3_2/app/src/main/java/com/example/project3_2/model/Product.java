package com.example.project3_2.model;

import java.io.Serializable;

public class Product implements Serializable {
    int id;
    String name;
    int price;
    String describe;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", describe='" + describe + '\'' +
                '}';
    }
}
