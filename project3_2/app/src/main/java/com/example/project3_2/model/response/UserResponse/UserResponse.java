package com.example.project3_2.model.response.UserResponse;

import com.example.project3_2.model.response.BaseResponse;

public class UserResponse extends BaseResponse {
    int idUser;
    String url_image;

    public UserResponse() {
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }
}