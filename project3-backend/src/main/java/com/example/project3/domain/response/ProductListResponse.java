package com.example.project3.domain.response;

import com.example.project3.model.Product;
import com.example.project3.service.CommonService;

import java.util.List;

public class ProductListResponse extends BaseResponse{
    List<Product> productList;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
