package com.example.project3.domain.request;

import com.example.project3.model.PageAction;

import java.util.List;

public class CreateRoleRequest {
    int idRole;
    String nameRole;
    List<PageAction> pageActions;

    public CreateRoleRequest() {
    }

    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    public List<PageAction> getPageActions() {
        return pageActions;
    }

    public void setPageActions(List<PageAction> pageActions) {
        this.pageActions = pageActions;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }
}
