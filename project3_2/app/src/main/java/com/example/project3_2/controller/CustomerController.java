package com.example.project3_2.controller;

import android.util.Log;
import android.widget.Toast;

import com.example.project3_2.activity.CustomerActivity;
import com.example.project3_2.model.CustomerModel;
import com.example.project3_2.model.UserModel;
import com.example.project3_2.model.response.CustomerResponse.CustomerListResponse;
import com.example.project3_2.model.response.UserResponse.UserListResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {
    List<CustomerModel> customerModels;

    public List<CustomerModel> getAll(String token) {
        customerModels = new ArrayList<>();
        try {
            URL url = new URL(CommonController.IP_ADDRESS + "/customer/getAll");
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
                CustomerListResponse res = new Gson().fromJson(response.toString(), CustomerListResponse.class);
                Log.v("response", response.toString());
                return res.getCustomerList();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return customerModels;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return customerModels;
    }
}
