package com.example.project3.controller;

import com.example.project3.domain.response.CustomerListResponse;
import com.example.project3.domain.response.ProductListResponse;
import com.example.project3.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @CrossOrigin(origins = "*")
    @GetMapping("/getAll")
    public ResponseEntity<ProductListResponse> getAll(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(productService.getAll(token));
    }
}
