package com.example.project3_2.controller;

import android.util.Log;

import com.example.project3_2.model.response.BaseResponse;
import com.example.project3_2.model.response.HistoryCareResponse.HistoryCareListResponse;
import com.example.project3_2.model.response.TransactionResponse.TransactionListResponse;
import com.example.project3_2.model.response.TransactionResponse.TransactionRes;
import com.example.project3_2.model.response.TransactionResponse.TransactionResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HistoryCareController {
    public BaseResponse updateStatusUser(String token, int id) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            URL url = new URL(CommonController.IP_ADDRESS + "/historyCare/updateStatusUser/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Authorization", token);
            connection.setDoOutput(true);

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                baseResponse = new Gson().fromJson(response.toString(), BaseResponse.class);
                Log.v("baseResponse", response.toString());
                return baseResponse;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return baseResponse;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return baseResponse;
    }

    public BaseResponse refreshStatusUser(String token, int id) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            URL url = new URL(CommonController.IP_ADDRESS + "/historyCare/refreshStatusUser/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Authorization", token);
            connection.setDoOutput(true);

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                baseResponse = new Gson().fromJson(response.toString(), BaseResponse.class);
                Log.v("baseResponse", response.toString());
                return baseResponse;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return baseResponse;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return baseResponse;
    }

    public HistoryCareListResponse getHistoryCaresByIdCustomer(String token, int id) {
        HistoryCareListResponse historyCareListResponse = new HistoryCareListResponse();
        try {
            URL url = new URL(CommonController.IP_ADDRESS + "/historyCare/getHistoryCaresByIdCustomer/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Authorization", token);
            connection.setDoOutput(true);

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                historyCareListResponse = new Gson().fromJson(response.toString(), HistoryCareListResponse.class);
                Log.v("historyCareListResponse", response.toString());
                return historyCareListResponse;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return historyCareListResponse;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return historyCareListResponse;
    }

    public BaseResponse createHistoryCare(String token, int idCustomer,int idUser,String time,String note) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            URL url = new URL(CommonController.IP_ADDRESS + "/historyCare/create");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Authorization", token);
            connection.setDoOutput(true);

            String jsonInputString =
                    "{\"idCustomer\":" + idCustomer + "," +
                            "\"idUser\":" + idUser + "," +
                            "\"time\": \"" + time + "\"," +
                            "\"note\":\"" + note + "\"}";
            OutputStream os = connection.getOutputStream();
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                baseResponse = new Gson().fromJson(response.toString(), BaseResponse.class);
                Log.v("baseResponse", response.toString());
                return baseResponse;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return baseResponse;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return baseResponse;
    }
}
