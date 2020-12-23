package com.example.project3_2.model.response.ProductResponse;

import com.example.project3_2.model.Product;
import com.example.project3_2.model.response.BaseResponse;

import java.util.List;

public class ProductResponse extends BaseResponse {
    List<Product> productList;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
