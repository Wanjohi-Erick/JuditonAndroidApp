package com.rickieyinnovates.juditon;

import com.rickieyinnovates.juditon.models.Account;
import com.rickieyinnovates.juditon.models.BankAccount;

import java.util.List;

public class ApiDataManager {
    private static ApiDataManager instance;
    private String authToken;
    private String username;

    private List<BankAccount> bankAccounts;

    private List<Account> accountList;
    private ApiDataManager() {
    }

    public static ApiDataManager getInstance() {
        if (instance == null) {
            instance = new ApiDataManager();
        }
        return instance;
    }

    public void setAuthToken(String token) {
        this.authToken = token;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }
}