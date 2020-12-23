package com.example.project3_2.model.response.UserResponse;

import com.example.project3_2.model.UserModel;
import com.example.project3_2.model.response.BaseResponse;

import java.util.List;

public class UserListResponse extends BaseResponse {
    List<UserModel> userList;

    public UserListResponse() {
    }

    public List<UserModel> getUserList() {
        return userList;
    }

    public void setUserList(List<UserModel> userList) {
        this.userList = userList;
    }
}
