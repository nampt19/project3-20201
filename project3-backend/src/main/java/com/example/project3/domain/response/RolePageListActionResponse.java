package com.example.project3.domain.response;

import com.example.project3.model.RolePageAction;

import java.util.List;

public class RolePageListActionResponse extends BaseResponse {
    private List<RolePageAction> rolePageActionList;

    public List<RolePageAction> getRolePageActionList() {
        return rolePageActionList;
    }

    public void setRolePageActionList(List<RolePageAction> rolePageActionList) {
        this.rolePageActionList = rolePageActionList;
    }
}
