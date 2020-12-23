package com.example.project3_2.model.response.RoleResponse;

import com.example.project3_2.model.response.BaseResponse;

import java.util.ArrayList;

public class RoleListReponse extends BaseResponse {
    ArrayList<Role> roleList;

    public ArrayList<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(ArrayList<Role> roleList) {
        this.roleList = roleList;
    }
}
