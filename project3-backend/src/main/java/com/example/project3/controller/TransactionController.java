package com.example.project3.controller;

import com.example.project3.domain.request.CreateTransactionRequest;
import com.example.project3.domain.response.BaseResponse;
import com.example.project3.domain.response.ProductListResponse;
import com.example.project3.domain.response.TransactionListResponse;
import com.example.project3.domain.response.TransactionResponse;
import com.example.project3.model.Transaction;
import com.example.project3.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @CrossOrigin(origins = "*")
    @GetMapping("/getAll")
    public ResponseEntity<TransactionListResponse> getAll(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(transactionService.getAll(token));
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/getTransactionById/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@RequestHeader("Authorization") String token, @PathVariable int id) {
        return ResponseEntity.ok(transactionService.getTransactionById(token,id));
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/create")
    public ResponseEntity<BaseResponse> createTransaction(@RequestHeader("Authorization") String token, @RequestBody Transaction request) {
        return ResponseEntity.ok(transactionService.createTransaction(token,request));
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/edit")
    public ResponseEntity<BaseResponse> editTransaction(@RequestHeader("Authorization") String token, @RequestBody Transaction request) {
        return ResponseEntity.ok(transactionService.editTransaction(token,request));
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> deleteTransaction(@RequestHeader("Authorization") String token,@PathVariable int id) {
        return ResponseEntity.ok(transactionService.deleteTransaction(token,id));
    }
}
