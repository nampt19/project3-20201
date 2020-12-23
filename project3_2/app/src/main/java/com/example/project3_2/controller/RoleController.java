package com.example.project3_2.controller;

import android.util.Log;

import com.example.project3_2.model.response.LoginResponse.LoginResponse;
import com.example.project3_2.model.response.RoleResponse.Role;
import com.example.project3_2.model.response.RoleResponse.RoleListReponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RoleController {
    public List<Role> getAllRole(String token){
                List<Role> roleList = new ArrayList<>();
        try {
            URL url = new URL(CommonController.IP_ADDRESS+"/role/roleList");
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
                RoleListReponse roleListReponse = new Gson().fromJson(response.toString(),RoleListReponse.class);
                roleList = roleListReponse.getRoleList();
                Log.v("response", response.toString());
                Log.v("rolelist", roleList.size()+"");
                return roleList;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return roleList;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return roleList;
    }
}
