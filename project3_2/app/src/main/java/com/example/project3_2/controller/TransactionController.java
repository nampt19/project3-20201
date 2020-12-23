package com.example.project3_2.controller;

import android.util.Log;

import com.anychart.scales.DateTime;
import com.example.project3_2.model.Product;
import com.example.project3_2.model.response.BaseResponse;
import com.example.project3_2.model.response.ProductResponse.ProductResponse;
import com.example.project3_2.model.response.TransactionResponse.TransactionListResponse;
import com.example.project3_2.model.response.TransactionResponse.TransactionRes;
import com.example.project3_2.model.response.TransactionResponse.TransactionResponse;
import com.example.project3_2.model.response.UserResponse.UserResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionController {
    List<TransactionRes> transactionResList;

    public List<TransactionRes> getAll(String token) {
        transactionResList = new ArrayList<>();
        try {
            URL url = new URL(CommonController.IP_ADDRESS + "/transaction/getAll");
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
                TransactionListResponse res = new Gson().fromJson(response.toString(), TransactionListResponse.class);
                Log.v("response product list", response.toString());
                return res.getTransactionList();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return transactionResList;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return transactionResList;
    }

    public TransactionResponse getTransactionById(String token, int id) {
        TransactionResponse transactionResponse = new TransactionResponse();
        try {
            URL url = new URL(CommonController.IP_ADDRESS + "/transaction/getTransactionById/" + id);
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
                transactionResponse = new Gson().fromJson(response.toString(), TransactionResponse.class);
                Log.v("transactionResponse", response.toString());
                return transactionResponse;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return transactionResponse;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return transactionResponse;
    }

    public BaseResponse updateTransaction(String token, int idTransaction, String statusTransacTion, String noteTransaction) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            URL url = new URL(CommonController.IP_ADDRESS + "/transaction/edit");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Authorization", token);
            connection.setDoOutput(true);

            String jsonInputString =
                    "{\"id\":" + idTransaction + ", \"status\": \"" + statusTransacTion + "\",\"note\":\"" + noteTransaction + "\"}";
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

    public BaseResponse createTransaction(String token, int idCustomer, int idUser, int idProduct, String status, String note) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            URL url = new URL(CommonController.IP_ADDRESS + "/transaction/create");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Authorization", token);
            connection.setDoOutput(true);
            String jsonInputString =
                    "{\"idCustomer\":" + idCustomer + "," +
                            "\"idUser\":" + idUser + "," +
                            "\"idProduct\":" + idUser + "," +
                            "\"status\": \"" + status + "\"," +
                            "\"note\":\"" + note + "\"}";
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
