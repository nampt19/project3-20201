package com.example.project3.service;

import com.example.project3.domain.response.CustomerListResponse;
import com.example.project3.model.Customer;
import com.example.project3.model.User;
import com.example.project3.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService extends CommonService {
    @Autowired
    CustomerRepository customerRepository;


    public CustomerListResponse getAllCustomer(String token) {
        CustomerListResponse customerListResponse = new CustomerListResponse();
        if (checkToken(token)) {
            List<Customer> customers = customerRepository.findAll();
            customerListResponse.setCustomerList(customers);
            customerListResponse.setCode("100");
            customerListResponse.setMessage("success");
        } else {
            customerListResponse.setCode("111");
            customerListResponse.setMessage("not token");
        }

        return customerListResponse;
    }
}
