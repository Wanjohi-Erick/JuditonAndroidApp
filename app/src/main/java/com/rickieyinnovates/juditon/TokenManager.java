package com.rickieyinnovates.juditon;

import com.rickieyinnovates.juditon.models.BankAccount;

import java.util.List;

public class TokenManager {
    private static TokenManager instance;
    private String authToken;
    private String username;

    private List<BankAccount> bankAccounts;

    private TokenManager() {
    }

    public static TokenManager getInstance() {
        if (instance == null) {
            instance = new TokenManager();
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
}