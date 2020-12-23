package com.example.project3.domain.request;

import com.example.project3.model.PageAction;

import java.util.List;

public class EditRoleRequest {
    int idRole;
    List<PageAction> pageActions;


    public EditRoleRequest() {
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public List<PageAction> getPageActions() {
        return pageActions;
    }

    public void setPageActions(List<PageAction> pageActions) {
        this.pageActions = pageActions;
    }
}
