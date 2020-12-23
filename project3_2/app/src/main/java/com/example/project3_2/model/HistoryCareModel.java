package com.example.project3_2.model;

import java.io.Serializable;

public class HistoryCareModel implements Serializable {
    private  String time;
    private  String note;
    private String userName;

    public HistoryCareModel() {
    }

    public HistoryCareModel(String time, String note, String userName) {
        this.time = time;
        this.note = note;
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
