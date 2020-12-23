package com.example.project3.service;

import com.example.project3.domain.request.CreateTransactionRequest;
import com.example.project3.domain.response.BaseResponse;
import com.example.project3.domain.response.TransactionListResponse;
import com.example.project3.domain.response.TransactionRes;
import com.example.project3.domain.response.TransactionResponse;
import com.example.project3.model.Customer;
import com.example.project3.model.Product;
import com.example.project3.model.Transaction;
import com.example.project3.model.User;
import com.example.project3.repository.CustomerRepository;
import com.example.project3.repository.ProductRepository;
import com.example.project3.repository.TransactionRepository;
import com.example.project3.repository.UserRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService extends CommonService {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CustomerRepository customerRepository;

    public TransactionListResponse getAll(String token) {
        TransactionListResponse listResponse = new TransactionListResponse();
        if (checkToken(token)) {
            List<Transaction> transactions = transactionRepository.findAll();
            List<TransactionRes> res = new ArrayList<>();
            for (Transaction transaction : transactions) {
                int id = transaction.getId();
                Date time = transaction.getTime();
                int price = productRepository.findById(transaction.getIdProduct()).get().getPrice();
                String customerName = customerRepository.findById(transaction.getIdCustomer()).get().getName();
                String status = transaction.getStatus();
                res.add(new TransactionRes(id, customerName, time, price, status));
            }
            listResponse.setTransactionList(res);
            listResponse.setCode("100");
            listResponse.setMessage("success");
        } else {
            listResponse.setCode("111");
            listResponse.setMessage("not token");
        }
        return listResponse;
    }

    public TransactionResponse getTransactionById(String token, int id) {
        TransactionResponse response = new TransactionResponse();
        if (checkToken(token)) {
            Transaction transaction = transactionRepository.findById(id).get();

            int idTransaction = transaction.getId();
            Date time = transaction.getTime();
            String customerName = customerRepository.findById(transaction.getIdCustomer()).get().getName();
            String customerPhone = customerRepository.findById(transaction.getIdCustomer()).get().getPhone();
            String userName = userRepository.findById(transaction.getIdCustomer()).get().getName();
            String status = transaction.getStatus();

            Product product = productRepository.findById(transaction.getIdProduct()).get();
            String note = transaction.getNote();
            response.setId(idTransaction);
            response.setTime(time);
            response.setCustomerName(customerName);
            response.setCustomerPhone(customerPhone);
            response.setUserName(userName);
            response.setProduct(product);
            response.setStatus(status);
            response.setNote(note);

            response.setCode("100");
            response.setMessage("success");
        } else {
            response.setCode("111");
            response.setMessage("not token");
        }
        return response;
    }

    public BaseResponse createTransaction(String token, Transaction request) {
        BaseResponse baseResponse = new BaseResponse();
        if (checkToken(token)) {
            request.setTime(new Date());
            transactionRepository.save(request);
            baseResponse.setCode("100");
            baseResponse.setMessage("Success");
        } else {
            baseResponse.setCode("111");
            baseResponse.setMessage("no token");
        }
        return baseResponse;
    }

    public BaseResponse editTransaction(String token, Transaction request) {
        BaseResponse baseResponse = new BaseResponse();
        if (checkToken(token)) {
            Transaction transaction = transactionRepository.findById(request.getId()).get();
            transaction.setNote(request.getNote());
            transaction.setStatus(request.getStatus());
            transactionRepository.save(transaction);
            baseResponse.setCode("100");
            baseResponse.setMessage("Success");
        } else {
            baseResponse.setCode("111");
            baseResponse.setMessage("no token");
        }
        return baseResponse;
    }

    public BaseResponse deleteTransaction(String token, int id) {
        BaseResponse baseResponse = new BaseResponse();
        if (checkToken(token)) {
            transactionRepository.deleteById(id);
            baseResponse.setCode("100");
            baseResponse.setMessage("Success");
        } else {
            baseResponse.setCode("111");
            baseResponse.setMessage("no token");
        }
        return baseResponse;
    }
}
