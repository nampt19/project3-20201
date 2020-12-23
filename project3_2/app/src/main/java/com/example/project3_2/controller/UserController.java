package com.example.project3_2.controller;


import android.util.Log;

import com.example.project3_2.activity.UserActivity;
import com.example.project3_2.helper.CachingFile;
import com.example.project3_2.model.UserModel;
import com.example.project3_2.model.response.BaseResponse;
import com.example.project3_2.model.response.LoginResponse.LoginResponse;
import com.example.project3_2.model.response.RoleResponse.RoleListReponse;
import com.example.project3_2.model.response.UserResponse.UserListResponse;
import com.example.project3_2.model.response.UserResponse.UserRequest;
import com.example.project3_2.model.response.UserResponse.UserResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UserController {

    public UserResponse createUser(UserRequest userRequest) {
        UserResponse userResponse = new UserResponse();
        try {
            String token = userRequest.getToken();
            Log.v("emailUsser",userRequest.getEmail());
            URL url = new URL(CommonController.IP_ADDRESS+"/user/create");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Authorization", token);
            connection.setDoOutput(true);

            // String jsonInputString = "{\"email\": \"nam.pt@gmail.com\", \"password\": \"123\"}";
            String jsonInputString = new Gson().toJson(userRequest);
            OutputStream os = connection.getOutputStream();
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                userResponse = new Gson().fromJson(response.toString(), UserResponse.class);
                  Log.v("userResponse", response.toString());
                return  userResponse;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return  userResponse;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return  userResponse;

    }

    public List<UserModel> getAllUserExceptAdmin(String token){
        List<UserModel> userModelList = new ArrayList<>();
        try {
            URL url = new URL(CommonController.IP_ADDRESS+"/user/getAll");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", token);

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                UserListResponse userListResponse = new Gson().fromJson(response.toString(), UserListResponse.class);
                Log.v("response", response.toString());
                return userListResponse.getUserList();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return userModelList;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userModelList;
    }


    public UserResponse updateUser(UserRequest userRequest) {
        UserResponse userResponse = new UserResponse();
        try {
            String token = userRequest.getToken();
          //  Log.v("emailUsser",userRequest.getEmail());
            URL url = new URL(CommonController.IP_ADDRESS+"/user/update");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Authorization", token);
            connection.setDoOutput(true);

            // String jsonInputString = "{\"email\": \"nam.pt@gmail.com\", \"password\": \"123\"}";
            String jsonInputString = new Gson().toJson(userRequest);
            OutputStream os = connection.getOutputStream();
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                userResponse = new Gson().fromJson(response.toString(), UserResponse.class);
                Log.v("userResponse", response.toString());
                return  userResponse;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return  userResponse;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return  userResponse;

    }

    public BaseResponse deleteUser(String token, int idUser) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            URL url = new URL(CommonController.IP_ADDRESS+"/user/delete/"+idUser);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Authorization", token);
            connection.setDoOutput(true);

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                baseResponse = new Gson().fromJson(response.toString(), BaseResponse.class);
                Log.v("baseResponseDeleteUser", response.toString());
                return  baseResponse;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return  baseResponse;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return  baseResponse;

    }
}
