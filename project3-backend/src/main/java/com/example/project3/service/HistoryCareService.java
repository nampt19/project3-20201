package com.example.project3.service;

import com.example.project3.domain.response.BaseResponse;
import com.example.project3.domain.response.HistoryCareListResponse;
import com.example.project3.domain.response.HistoryCareRes;
import com.example.project3.model.Customer;
import com.example.project3.model.HistoryCare;
import com.example.project3.model.User;
import com.example.project3.repository.CustomerRepository;
import com.example.project3.repository.HistoryCareRepository;
import com.example.project3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryCareService extends CommonService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    HistoryCareRepository historyCareRepository;

    @Autowired
    UserRepository userRepository;

    public BaseResponse updateStatusUser(String token, int id) {
        BaseResponse baseResponse = new BaseResponse();
        if (checkToken(token)) {
            Customer customer = customerRepository.getOne(id);
            if (customer.getStatus().equals("access")) {
                customer.setStatus("inaccess");
                customerRepository.save(customer);
                baseResponse.setCode("100");
                baseResponse.setMessage("success");
            } else {
                baseResponse.setCode("101");
                baseResponse.setMessage("customer is caring");
            }
        } else {
            baseResponse.setCode("111");
            baseResponse.setMessage("no token");
        }
        return baseResponse;
    }

    public BaseResponse refreshStatusUser(String token, int id) {
        BaseResponse baseResponse = new BaseResponse();
        if (checkToken(token)) {
            Customer customer = customerRepository.getOne(id);
            customer.setStatus("access");
            customerRepository.save(customer);
            baseResponse.setCode("100");
            baseResponse.setMessage("success");
        } else {
            baseResponse.setCode("111");
            baseResponse.setMessage("no token");
        }
        return baseResponse;
    }

    public HistoryCareListResponse getHistoryCaresByIdCustomer(String token, int id) {
        HistoryCareListResponse response = new HistoryCareListResponse();
        if (checkToken(token)) {
            List<HistoryCare> historyCares = historyCareRepository.findAllByIdCustomer(id);
            List<HistoryCareRes> res = new ArrayList<>();
            for (HistoryCare historyCare : historyCares) {
                String userName = userRepository.getOne(historyCare.getIdUser()).getName();
                String time = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(historyCare.getTime());
                String note = historyCare.getNote();
                res.add(new HistoryCareRes(userName, time, note));
            }
            response.setHistoryCareList(res);
            response.setCode("100");
            response.setMessage("success");
        } else {
            response.setCode("111");
            response.setMessage("no token");
        }
        return response;
    }

    public BaseResponse createHistoryCare(String token, HistoryCare historyCare) {
        BaseResponse response = new BaseResponse();
        if (checkToken(token)) {
            historyCareRepository.save(historyCare);
            response.setCode("100");
            response.setMessage("success");
        } else {
            response.setCode("111");
            response.setMessage("no token");
        }
        return response;
    }
}
