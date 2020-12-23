package com.example.project3_2.controller;

import android.util.Log;

import com.example.project3_2.model.CustomerModel;
import com.example.project3_2.model.Product;
import com.example.project3_2.model.response.CustomerResponse.CustomerListResponse;
import com.example.project3_2.model.response.ProductResponse.ProductResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProductController {

    List<Product> products;

    public List<Product> getAll(String token) {
        products = new ArrayList<>();
        try {
            URL url = new URL(CommonController.IP_ADDRESS + "/product/getAll");
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
                ProductResponse res = new Gson().fromJson(response.toString(), ProductResponse.class);
                Log.v("response product list", response.toString());
                return res.getProductList();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return products;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return products;
    }
}
