package com.example.project3.service;

import com.example.project3.domain.response.ProductListResponse;
import com.example.project3.model.Customer;
import com.example.project3.model.Product;
import com.example.project3.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService extends CommonService {
    @Autowired
    ProductRepository productRepository;


    public ProductListResponse getAll(String token) {
        ProductListResponse productListResponse = new ProductListResponse();
        if (checkToken(token)) {
            List<Product> products = productRepository.findAll();
            productListResponse.setProductList(products);
            productListResponse.setCode("100");
            productListResponse.setMessage("success");
        } else {
            productListResponse.setCode("111");
            productListResponse.setMessage("not token");
        }

        return productListResponse;
    }


}
