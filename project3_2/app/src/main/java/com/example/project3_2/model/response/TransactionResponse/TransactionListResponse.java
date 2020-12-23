package com.example.project3_2.model.response.TransactionResponse;

import com.example.project3_2.model.response.BaseResponse;

import java.util.List;

public class TransactionListResponse  extends BaseResponse {
    private List<TransactionRes> transactionList;

    public List<TransactionRes> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<TransactionRes> transactionList) {
        this.transactionList = transactionList;
    }



}