package com.example.project3.domain.response;

import java.util.Date;

public class HistoryCareRes {
    String userName;
    String time;
    String note;

    public HistoryCareRes() {
    }

    public HistoryCareRes(String userName, String time, String note) {
        this.userName = userName;
        this.time = time;
        this.note = note;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
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
}
