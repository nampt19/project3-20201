package com.example.project3.domain.response;

import com.example.project3.model.Customer;

import java.util.List;

public class CustomerListResponse extends BaseResponse{
    List<Customer> customerList;


    public CustomerListResponse() {
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }
}
