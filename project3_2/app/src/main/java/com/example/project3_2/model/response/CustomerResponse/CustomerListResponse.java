package com.example.project3_2.model.response.CustomerResponse;

import com.example.project3_2.model.CustomerModel;
import com.example.project3_2.model.response.BaseResponse;

import java.util.List;

public class CustomerListResponse extends BaseResponse {
    private List<CustomerModel> customerList;

    public List<CustomerModel> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<CustomerModel> customerList) {
        this.customerList = customerList;
    }
}
