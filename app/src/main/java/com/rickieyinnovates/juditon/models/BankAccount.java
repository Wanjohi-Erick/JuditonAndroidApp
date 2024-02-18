package com.rickieyinnovates.juditon.models;

public class BankAccount {

    private Integer id;

    private String accountName;

    private String account;

    private String bankName;

    private String type;

    private Double balance;


    public BankAccount(Integer id, String accountName, String account, String bankName, String type, Double balance) {
        this.id = id;
        this.accountName = accountName;
        this.account = account;
        this.bankName = bankName;
        this.type = type;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}