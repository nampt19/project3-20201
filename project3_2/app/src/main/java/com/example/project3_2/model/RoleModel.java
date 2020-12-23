package com.example.project3_2.model;

import java.io.Serializable;

public class RoleModel implements Serializable {
    String name;
    String describe;
    public RoleModel() {
    }

    public RoleModel(String name, String describe) {
        this.name = name;
        this.describe = describe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
