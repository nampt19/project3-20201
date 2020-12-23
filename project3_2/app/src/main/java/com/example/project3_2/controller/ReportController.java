package com.example.project3_2.controller;

import android.util.Log;

import com.example.project3_2.model.CustomerModel;
import com.example.project3_2.model.response.CustomerResponse.CustomerListResponse;
import com.example.project3_2.model.response.ReportResponse.ReportResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReportController {
    public ReportResponse getAll(String token) {
        ReportResponse  reportResponse = new ReportResponse();
        try {
            URL url = new URL(CommonController.IP_ADDRESS + "/report/getAll");
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
                ReportResponse res = new Gson().fromJson(response.toString(), ReportResponse.class);
                Log.v("report-response", response.toString());
                return res;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return reportResponse;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return reportResponse;
    }

    public ReportResponse getByTime(String token,int seconds) {
        ReportResponse  reportResponse = new ReportResponse();
        try {
            URL url = new URL(CommonController.IP_ADDRESS + "/report/getBytime/"+seconds);
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
                ReportResponse res = new Gson().fromJson(response.toString(), ReportResponse.class);
                Log.v("reportbyTime-response", response.toString());
                return res;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return reportResponse;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return reportResponse;
    }
}
