package com.example.project3.domain.response;

import com.example.project3.model.User;

import java.util.List;

public class UserListResponse extends BaseResponse {
    List<User> userList;

    public UserListResponse() {
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
