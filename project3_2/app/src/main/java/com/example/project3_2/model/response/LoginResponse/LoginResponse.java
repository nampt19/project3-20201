package com.example.project3_2.model.response.LoginResponse;

import com.example.project3_2.model.response.BaseResponse;

import java.util.List;

public class LoginResponse extends BaseResponse {
    private  int idUser;
    private List<PageAction> pageActions;

    public LoginResponse() {
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public List<PageAction> getPageActions() {
        return pageActions;
    }

    public void setPageActions(List<PageAction> pageActions) {
        this.pageActions = pageActions;
    }
}
