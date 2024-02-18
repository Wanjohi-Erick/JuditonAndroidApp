package com.rickieyinnovates.juditon.models;

import java.time.LocalDate;

public class Transaction {
    String payerOrPayee;
    Double amount;
    Double transactionCost;
    String date;
    String transactionReferenece;

    public Transaction() {
    }

    public Transaction(String payerOrPayee, Double amount, Double transactionCost, String date, String transactionReferenece) {
        this.payerOrPayee = payerOrPayee;
        this.amount = amount;
        this.transactionCost = transactionCost;
        this.date = date;
        this.transactionReferenece = transactionReferenece;
    }

    public String getPayerOrPayee() {
        return payerOrPayee;
    }

    public void setPayerOrPayee(String payerOrPayee) {
        this.payerOrPayee = payerOrPayee;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getTransactionCost() {
        return transactionCost;
    }

    public void setTransactionCost(Double transactionCost) {
        this.transactionCost = transactionCost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransactionReferenece() {
        return transactionReferenece;
    }

    public void setTransactionReferenece(String transactionReferenece) {
        this.transactionReferenece = transactionReferenece;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "payerOrPayee='" + payerOrPayee + '\'' +
                ", amount=" + amount +
                ", transactionCost=" + transactionCost +
                ", date='" + date + '\'' +
                ", transactionReferenece='" + transactionReferenece + '\'' +
                '}';
    }
}
