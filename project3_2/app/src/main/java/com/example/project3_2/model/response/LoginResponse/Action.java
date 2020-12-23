package com.example.project3_2.model.response.LoginResponse;

import java.io.Serializable;

public class Action implements Serializable {
    private int id;
    private String name;

    public Action() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
