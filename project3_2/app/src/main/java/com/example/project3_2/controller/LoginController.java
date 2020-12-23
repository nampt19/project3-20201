package com.example.project3_2.controller;

import android.util.Log;

import com.example.project3_2.model.Login;
import com.example.project3_2.model.response.BaseResponse;
import com.example.project3_2.model.response.LoginResponse.LoginResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginController {

    public String checkLogin(Login login){
        String strResponse = "";
        try {
            URL url = new URL(CommonController.IP_ADDRESS+"/user/login");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "*/*");
            connection.setDoOutput(true);

            // String jsonInputString = "{\"email\": \"nam.pt@gmail.com\", \"password\": \"123\"}";
            String jsonInputString = new Gson().toJson(login);
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
                strResponse = response.toString();
//                Log.v("response", strResponse);
                //loginResponse = new Gson().fromJson(response.toString(), LoginResponse.class);
                  Log.v("loginResponse", strResponse);
                return strResponse;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return strResponse;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return strResponse;
    };

    public BaseResponse checkLogout(String s){
        BaseResponse baseResponse = new BaseResponse();
        try {
            String token = s;
            URL url = new URL(CommonController.IP_ADDRESS+"/user/logout");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Authorization", token);
            connection.setDoOutput(true);

            String jsonInputString = "";
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
                // Log.v("response", response.toString());
                baseResponse = new Gson().fromJson(response.toString(), LoginResponse.class);
                Log.d("response", baseResponse.getCode());
                return baseResponse;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return baseResponse;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return baseResponse;
    };
}
