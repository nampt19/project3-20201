package com.example.project3.model;

public class Report {
    int numberCustomer;
    int numberTransaction;
    int numberHistoryCare;

    int numberStatusPaid;
    int numberStatusPaying;
    int numberStatusDestroy;

    int moneyStatusPaid;
    int moneyStatusPaying;
    int moneyStatusDestroy;

    public Report() {
    }

    public Report(int numberCustomer, int numberTransaction, int numberHistoryCare, int numberStatusPaid, int numberStatusPaying, int numberStatusDestroy, int moneyStatusPaid, int moneyStatusPaying, int moneyStatusDestroy) {
        this.numberCustomer = numberCustomer;
        this.numberTransaction = numberTransaction;
        this.numberHistoryCare = numberHistoryCare;
        this.numberStatusPaid = numberStatusPaid;
        this.numberStatusPaying = numberStatusPaying;
        this.numberStatusDestroy = numberStatusDestroy;
        this.moneyStatusPaid = moneyStatusPaid;
        this.moneyStatusPaying = moneyStatusPaying;
        this.moneyStatusDestroy = moneyStatusDestroy;
    }

    public int getNumberCustomer() {
        return numberCustomer;
    }

    public void setNumberCustomer(int numberCustomer) {
        this.numberCustomer = numberCustomer;
    }

    public int getNumberTransaction() {
        return numberTransaction;
    }

    public void setNumberTransaction(int numberTransaction) {
        this.numberTransaction = numberTransaction;
    }

    public int getNumberHistoryCare() {
        return numberHistoryCare;
    }

    public void setNumberHistoryCare(int numberHistoryCare) {
        this.numberHistoryCare = numberHistoryCare;
    }

    public int getNumberStatusPaid() {
        return numberStatusPaid;
    }

    public void setNumberStatusPaid(int numberStatusPaid) {
        this.numberStatusPaid = numberStatusPaid;
    }

    public int getNumberStatusPaying() {
        return numberStatusPaying;
    }

    public void setNumberStatusPaying(int numberStatusPaying) {
        this.numberStatusPaying = numberStatusPaying;
    }

    public int getNumberStatusDestroy() {
        return numberStatusDestroy;
    }

    public void setNumberStatusDestroy(int numberStatusDestroy) {
        this.numberStatusDestroy = numberStatusDestroy;
    }

    public int getMoneyStatusPaid() {
        return moneyStatusPaid;
    }

    public void setMoneyStatusPaid(int moneyStatusPaid) {
        this.moneyStatusPaid = moneyStatusPaid;
    }

    public int getMoneyStatusPaying() {
        return moneyStatusPaying;
    }

    public void setMoneyStatusPaying(int moneyStatusPaying) {
        this.moneyStatusPaying = moneyStatusPaying;
    }

    public int getMoneyStatusDestroy() {
        return moneyStatusDestroy;
    }

    public void setMoneyStatusDestroy(int moneyStatusDestroy) {
        this.moneyStatusDestroy = moneyStatusDestroy;
    }
}
