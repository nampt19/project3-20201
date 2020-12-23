package com.example.project3.domain.response;

import com.example.project3.model.Transaction;

import java.util.Date;
import java.util.List;

public class TransactionListResponse  extends BaseResponse{
    private List<TransactionRes> transactionList;

    public List<TransactionRes> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<TransactionRes> transactionList) {
        this.transactionList = transactionList;
    }



}
