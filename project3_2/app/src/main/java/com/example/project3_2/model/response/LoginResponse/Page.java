package com.example.project3_2.model.response.LoginResponse;

import java.io.Serializable;

public class Page implements Serializable {
    private int id;
    private String name;
    private String note;
    public Page() {
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
