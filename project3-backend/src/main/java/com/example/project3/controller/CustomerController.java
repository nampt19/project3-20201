package com.example.project3.controller;


import com.example.project3.domain.response.CustomerListResponse;
import com.example.project3.domain.response.UserListResponse;
import com.example.project3.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @CrossOrigin(origins = "*")
    @GetMapping("/getAll")
    public ResponseEntity<CustomerListResponse> getAllCustomer(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(customerService.getAllCustomer(token));
    }
}
